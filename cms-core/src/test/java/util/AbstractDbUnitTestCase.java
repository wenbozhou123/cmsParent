package util;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.dataset.xml.FlatXmlProducer;
import org.dbunit.operation.DatabaseOperation;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.xml.sax.InputSource;

import java.io.*;
import java.sql.SQLException;

public class AbstractDbUnitTestCase {

    public static IDatabaseConnection dbunitCon;
    private File tempFile;

    @BeforeClass
    public static void init() throws DatabaseUnitException,SQLException{
        dbunitCon = new DatabaseConnection(DbUtil.getConnection());
    }

    protected IDataSet createDataSet(String tname) throws DataSetException{
        InputStream is = AbstractDbUnitTestCase.class
                .getClassLoader().getResourceAsStream("dbunit_xml/"+ tname + ".xml");
        Assert.assertNotNull("dbunit的基本数据文件不存在", is);
        return new FlatXmlDataSetBuilder().build(is);
    }

    protected  void backupAllTable() throws SQLException, IOException, DataSetException{
        IDataSet ds = dbunitCon.createDataSet();
        writeBackupFile(ds);
    }

    private void writeBackupFile(IDataSet ds) throws IOException, DataSetException{
        tempFile = File.createTempFile("back", "xml");
        FlatXmlDataSet.write(ds, new FileWriter(tempFile));
    }

    protected void backupCustomerTable(String[] tname) throws DataSetException, IOException{
        QueryDataSet ds = new QueryDataSet(dbunitCon);
        for(String str : tname){
            ds.addTable(str);
        }
        writeBackupFile(ds);
    }

    protected void backupOneTable(String tname) throws DataSetException, IOException{
        backupCustomerTable(new String[]{tname});
    }

    protected void resumeTable() throws FileNotFoundException, DatabaseUnitException, SQLException{
        IDataSet ds = new FlatXmlDataSet(new FlatXmlProducer(new InputSource(new FileInputStream(tempFile))));
        DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
    }

    @AfterClass
    public static void destory(){
        try{
            if(dbunitCon != null) dbunitCon.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }













}

package util;

import com.bowen.cms.model.Role;
import com.bowen.cms.model.RoleType;
import com.bowen.cms.model.User;
import org.junit.Assert;

import java.util.List;

public class EntitiesHelper {

    private static User baseUser = new User(1, "admin1","123",
            "admin1", "admin1@admin.com", "110", 0);

    private static Role baseRole = new Role(1, "", RoleType.ROLE_ADMIN);

    public static void assertRole(Role expected, Role actual){
        Assert.assertNotNull(expected);
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getName(), actual.getName());
        Assert.assertEquals(expected.getRoleType(), actual.getRoleType());
    }

    public static void assertRole(List<Role> expected, List<Role> actual){
        for (int i =0; i< expected.size(); i++){
            Role eu = expected.get(i);
            Role au = actual.get(i);
            assertRole(eu, au);
        }
    }

    public static void assertUser(User expected, User actual){
        Assert.assertNotNull(expected);
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getUsername(), actual.getUsername());
        Assert.assertEquals(expected.getNickname(), actual.getNickname());
        Assert.assertEquals(expected.getPassword(), actual.getPassword());
        Assert.assertEquals(expected.getPhone(), actual.getPhone());
        Assert.assertEquals(expected.getStatus(), actual.getStatus());
        Assert.assertEquals(expected.getEmail(), actual.getEmail());
    }

    public static void assertUsers(List<User> expected, List<User> actual){
        for (int i =0; i< expected.size(); i++){
            User eu = expected.get(i);
            User au = actual.get(i);
            assertUser(eu, au);
        }
    }

    public static void assertUser(User expected){
        assertUser(expected, baseUser);
    }
}

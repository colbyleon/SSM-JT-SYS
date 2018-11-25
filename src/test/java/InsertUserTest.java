import com.jt.sys.entity.SysUser;
import com.jt.sys.service.SysUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class) //使用junit4进行测试
@ContextConfiguration(locations = {"classpath:spring-configs.xml"})
public class InsertUserTest {

    @Autowired
    private SysUserService userService;

    @Test
    public void insertUser() {
        SysUser user = new SysUser();
        user.setUsername("colby");
        user.setPassword("123456");
        user.setEmail("colby@qq.com");
        user.setMobile("13058065807");
        user.setValid(1);

        String s = userService.insertObject(user, "1");
        System.out.println(s);
    }

    @Test
    public void updateUser(){
        SysUser user = new SysUser();
        user.setUsername("colby");
        user.setPassword("123456");
        user.setEmail("colby@qq.com");
        user.setMobile("13058065807");
        String s = userService.updateObject(user, "1");
        System.out.println(s);
    }
}

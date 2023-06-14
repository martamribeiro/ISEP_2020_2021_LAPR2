package app.domain.model;

import org.junit.Assert;
import org.junit.Test;

public class OrgRoleTest {

    @Test(expected = IllegalArgumentException.class)
    public void ensureDescriptionNotEmpty(){
        OrgRole instance = new OrgRole("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureDescriptionNotNull(){
        OrgRole instance = new OrgRole(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureDescriptionNoLongerThan15Chars(){
        OrgRole instance = new OrgRole("The Person Who Talks To The Clients");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureDescriptionHasOnlyLetters(){
        OrgRole instance = new OrgRole("R3c3pc10n1st");
    }

    @Test
    public void equalsTrue(){
        OrgRole role1 = new OrgRole("Recepcionist");
        OrgRole role2 = new OrgRole("Recepcionist");
        boolean result = role1.equals(role2);
        Assert.assertTrue(result);
    }

    @Test
    public void equalsFalse(){
        OrgRole role1 = new OrgRole("Recepcionist");
        OrgRole role2 = new OrgRole("Administrator");
        boolean result = role1.equals(role2);
        Assert.assertFalse(result);
    }

    @Test
    public void equalsTrueToItself(){
        OrgRole role1 = new OrgRole("Recepcionist");
        boolean result = role1.equals(role1);
        Assert.assertTrue(result);
    }

    @Test
    public void equalsFalseDueToNull(){
        OrgRole role1 = new OrgRole("Recepcionist");
        boolean result = role1.equals(null);
        Assert.assertFalse(result);
    }

}
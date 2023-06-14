package app.domain.model;

import org.junit.Assert;
import org.junit.Test;

public class ReportTest {

    //Test 1
    @Test(expected = IllegalArgumentException.class)
    public void ensureNullIsNotAllowed(){
        Report report = new Report(null);
    }

    //Test 2
    @Test(expected = IllegalArgumentException.class)
    public void ensureEmptyIsNotAllowed(){
        Report report = new Report("");
    }

    //Test 3
    @Test(expected = IllegalArgumentException.class)
    public void ensureMoreThan400WordsAreNotAllowed(){
        Report report = new Report("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce vitae rutrum orci. Praesent vitae arcu urna. Ut ullamcorper non ex ullamcorper hendrerit. Phasellus convallis et dui ac tristique. Praesent id tristique sem. Sed ut sollicitudin mauris. Fusce libero enim, hendrerit et rhoncus vitae, efficitur eget arcu. Mauris et mauris congue massa venenatis venenatis. Vivamus semper varius interdum. Suspendisse efficitur, diam in efficitur imperdiet, sem nibh pharetra ante, a molestie tellus orci eget arcu. Etiam sit amet porttitor nunc. Proin vitae feugiat erat. Cras tempus elit aliquam interdum imperdiet. Proin fringilla massa ut odio varius ornare. Donec tempus, mauris quis interdum finibus, ligula quam mattis elit, eget consectetur urna nisl ut elit. Proin eget nisi scelerisque, rutrum ex sed, placerat nunc.\n" +
                "\n" +
                "Vestibulum ac diam sodales nulla interdum ultrices. Pellentesque consectetur nulla quis purus fringilla viverra. Mauris pharetra lectus laoreet erat pharetra, nec dignissim tellus blandit. Morbi sed rutrum metus, pretium semper mauris. Suspendisse in nibh nec turpis porttitor vulputate id non enim. Morbi bibendum mauris scelerisque pulvinar pulvinar. Cras fermentum, nibh in rutrum pulvinar, ipsum sapien vehicula tortor, ut suscipit sapien diam et ipsum. Integer diam massa, dignissim non aliquam at, cursus bibendum quam. Curabitur non libero metus. Aliquam ut lacus et massa sagittis luctus ac ac metus. Cras rhoncus dolor nec purus congue, a ornare purus volutpat. Pellentesque facilisis vel neque nec volutpat. Aliquam porta lorem ac leo elementum, quis hendrerit nisi vehicula.\n" +
                "\n" +
                "In hac habitasse platea dictumst. Vivamus hendrerit sapien sed urna ultrices tincidunt. Sed euismod mi in hendrerit tincidunt. Ut tincidunt facilisis ante, a suscipit nulla. Nam quam eros, blandit vitae nibh et, dictum efficitur quam. Ut sagittis in elit sed auctor. Praesent fermentum pellentesque dui, sed sagittis lorem placerat nec. Cras pretium ut ipsum eu fringilla. Curabitur aliquam sem non sapien feugiat, sed pulvinar quam tincidunt. Fusce pellentesque dolor mi, in porta mi ultrices sit amet.\n" +
                "\n" +
                "Donec molestie, ipsum vitae rutrum eleifend, arcu ante elementum ipsum, eget euismod lectus orci vel neque. Aliquam erat volutpat. Suspendisse ut ligula libero. Cras tincidunt a mauris ac interdum. Nam lobortis malesuada elit et consequat. Suspendisse iaculis lobortis augue eget sollicitudin. Pellentesque non posuere nibh, sit amet pharetra neque. Sed interdum mattis metus eu porta. Integer laoreet tristique odio, at ullamcorper elit tincidunt eu. Donec venenatis metus massa, non commodo urna dapibus ac. Aliquam aliquet, sem et pharetra tristique, enim ligula dictum diam, ut luctus arcu magna nec est. Vestibulum vehicula ornare.");
    }

    //Test 4
    @Test()
    public void ensure400WordsAreAllowed(){
        Report report = new Report("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum efficitur fringilla nibh, a accumsan purus imperdiet quis. In ac turpis eleifend, consectetur nibh eget, pellentesque elit. In a urna venenatis, euismod diam non, eleifend sapien. Maecenas ultricies vel magna sed lobortis. Fusce venenatis, mi et accumsan congue, leo ligula facilisis justo, non mollis nibh nulla sit amet velit. Suspendisse sed lacinia libero. Suspendisse eu ante eget justo ultricies tempor. Morbi id diam non dolor rutrum volutpat at sit amet ligula. Phasellus est enim, consequat nec mollis sit amet, aliquet in purus. Proin cursus lorem ac consequat lacinia. Praesent consectetur libero nec ipsum tincidunt congue. Maecenas volutpat dolor quis arcu venenatis, ac pellentesque nulla tempus.\n" +
                "\n" +
                "Maecenas turpis sem, placerat at ligula rutrum, facilisis egestas urna. Cras maximus a purus a rutrum. Sed orci sem, pharetra a velit in, pellentesque dignissim libero. Curabitur semper felis posuere ultrices vestibulum. Duis ultricies ex eu nibh fringilla efficitur. Aliquam erat volutpat. Nullam et lacus augue. Nunc vitae urna tempor odio aliquet fringilla. In hac habitasse platea dictumst. Aenean nec metus libero. Morbi eu euismod dui. Fusce tincidunt nibh eu nisl egestas iaculis. Suspendisse libero enim, porttitor at sapien at, egestas placerat diam. Morbi ultricies, tortor ut pharetra consectetur, sem dui sodales eros, at congue orci purus nec nisi. Aenean condimentum consequat convallis. Proin in gravida metus.\n" +
                "\n" +
                "Donec magna dolor, vehicula non pharetra a, pellentesque id lacus. Integer quis quam magna. Nullam finibus consectetur augue. Nulla vestibulum sit amet quam non tincidunt. Nulla odio orci, mattis sit amet ipsum ut, pharetra interdum eros. Cras in cursus sapien, eu egestas mauris. Morbi eu lectus magna. Sed lectus dui, tempor in nisl eget, vestibulum mattis nibh. Vestibulum suscipit elit at risus dictum tempor. Nam quis dui efficitur, rhoncus libero sed, condimentum urna. Cras arcu libero, tristique sed scelerisque eu, rhoncus in metus. Quisque tempor placerat malesuada. Vestibulum ut vestibulum ipsum, sit amet luctus nunc. In rhoncus condimentum justo, quis dignissim tortor fringilla ac. Aliquam at eros quis diam pharetra auctor.\n" +
                "\n" +
                "Vivamus a diam at nisl ullamcorper sodales. Quisque fringilla tincidunt semper. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Fusce et imperdiet ligula. Donec interdum, mi a rhoncus ullamcorper, velit quam pulvinar nulla, at ornare ante elit et elit. Praesent ullamcorper nisl lorem, sodales volutpat risus mattis sed. Sed lacinia consectetur ipsum non scelerisque. Curabitur et lorem eget est eleifend tempus. Cras at.");
    }

}
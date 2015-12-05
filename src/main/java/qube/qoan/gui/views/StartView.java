package qube.qoan.gui.views;

import com.kitfox.svg.pathcmd.Vertical;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import qube.qoan.gui.components.QoanHeader;

/**
 * Created by rainbird on 10/29/15.
 */
public class StartView extends VerticalLayout implements View {

    public static String NAME = "";

    private static String loremIpsum =
            "<p>"+
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus ut sapien vel lacus varius feugiat. " +
                    "Duis sit amet quam et ligula lacinia pulvinar non vitae massa. " +
                    "Vestibulum finibus sapien vel elit suscipit, " +
                    "et dapibus felis tincidunt. Ut porttitor felis purus. " +
                    "Integer facilisis quam id facilisis luctus. Quisque euismod libero arcu, vel fringilla enim pretium quis. " +
                    "Sed consequat consequat tortor ullamcorper tempor. Maecenas tincidunt sodales tellus quis pharetra. " +
                    "Donec imperdiet varius enim, a tempor nulla tristique quis. " +
                    "Aenean ac leo sed elit maximus maximus."+
            "</p>" +
            "<p text-align: center;>"+
            "Mauris metus nisi, pulvinar vel odio eget, tempus tristique velit. " +
                    "Sed venenatis libero velit, sit amet facilisis urna aliquam in. " +
                    "Nunc id nibh eget nisl interdum faucibus non nec odio. " +
                    "In venenatis finibus tortor, vel gravida orci malesuada ac. " +
                    "Ut mollis, eros nec blandit rhoncus, ipsum dui posuere nisi, ac tempor eros eros non dui. " +
                    "Praesent et vulputate justo, nec eleifend nibh. Proin eget cursus massa. " +
                    "Morbi eu ultrices eros. In a sodales ex. " +
                    "Morbi id molestie odio, at placerat metus. Proin mattis euismod fringilla. " +
                    "Curabitur eget dolor risus. Curabitur ornare sagittis purus, eu mollis leo vulputate in. " +
                    "Morbi fermentum augue eu nisi suscipit, sit amet fermentum dolor tristique. " +
                    "Proin lacus mauris, cursus in gravida sit amet, vestibulum non eros."+
            "</p>" +
            "<p text-align: center;>"+
                    "Etiam vulputate ante ut sapien aliquet feugiat. Vivamus sed lectus vitae felis egestas posuere imperdiet in nulla. " +
                    "Donec tincidunt posuere sem quis rhoncus. Duis congue diam et lectus pellentesque, quis ornare erat malesuada. " +
                    "Praesent malesuada eget neque nec maximus. Quisque quis imperdiet mauris, vel fringilla erat. " +
                    "In condimentum leo lorem, cursus porttitor felis ultricies laoreet. " +
                    "Maecenas magna nisl, posuere in nisl eu, tristique commodo est. " +
                    "Vestibulum finibus tortor nisi, id facilisis risus ultrices eget. " +
                    "Donec rutrum mauris augue, et aliquam lorem semper ac. " +
                    "Phasellus a felis turpis. Sed sed risus bibendum, vulputate dui ut, accumsan dolor. " +
                    "Vivamus faucibus turpis a ipsum viverra, at hendrerit nisi tincidunt." +
            "</p>" +
            "<p text-align: center;>"+
                    "Nunc fringilla, odio ac dignissim commodo, purus odio laoreet lectus, lobortis mollis ex turpis non leo. " +
                    "Fusce ac consequat risus. Quisque ut velit velit. " +
                    "Suspendisse vestibulum, eros sit amet dignissim pharetra, tellus nisl rutrum libero, " +
                    "et egestas risus augue non velit. Aenean nec nulla non sapien iaculis ullamcorper at in sem. " +
                    "Mauris eu ligula sed ex volutpat ultricies. Aliquam sollicitudin leo ipsum, et consectetur enim imperdiet tristique. " +
                    "Ut dignissim felis elit, vel suscipit quam molestie eget. " +
                    "Vestibulum sit amet nulla sollicitudin, vestibulum mi at, scelerisque purus. " +
                    "Cras imperdiet blandit ex ut malesuada. Praesent lacinia venenatis risus, vel fringilla nibh commodo in. " +
                    "Morbi facilisis diam lacinia aliquet molestie. Aenean eu magna faucibus diam ullamcorper eleifend. " +
                    "Nullam sodales dolor velit, id pharetra leo tempus vel. Aenean molestie nibh vitae quam convallis tempor. " +
                    "Donec varius mi sed libero sollicitudin dignissim a vitae mi."+
            "</p>" +
            "<p text-align: center;>"+
                    "Duis in turpis eu odio pharetra placerat. Curabitur sed nibh felis. " +
                    "Fusce lacinia auctor dui sit amet ultricies. Ut accumsan felis sit amet leo varius, at tristique magna iaculis. " +
                    "Maecenas rhoncus ipsum lectus, eget mollis turpis maximus quis. Etiam consequat varius neque, ac finibus turpis. " +
                    "Morbi hendrerit tincidunt scelerisque. Praesent faucibus dictum nisi. " +
                    "Aliquam pulvinar dapibus enim, rhoncus rutrum tortor placerat nec. " +
                    "Quisque lobortis ligula in augue finibus, suscipit sagittis elit lacinia. " +
                    "Sed accumsan imperdiet maximus. " +
            "</p>" ;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

        VerticalLayout layout = new VerticalLayout();
        layout.setWidth("100%");

        QoanHeader header = new QoanHeader();
        //header.setWidth("100%");
        layout.addComponent(header);

        UI.getCurrent().getPage().setTitle("Welcome to Qoan");

        //setWidth("1200px");
        VerticalLayout contentLayout = new VerticalLayout();
        contentLayout.setWidth("800px");
        Label label = new Label(loremIpsum, ContentMode.HTML);
        contentLayout.addComponent(label);
        contentLayout.setComponentAlignment(label, Alignment.TOP_RIGHT);
//        Button componentsButton = new Button("Components Display", new Button.ClickListener() {
//            @Override
//            public void buttonClick(Button.ClickEvent event) {
//                UI.getCurrent().getNavigator().navigateTo(ComponentsView.NAME);
//            }
//        });
//        addComponent(componentsButton);
//
//        Button workspaceButton = new Button("Workspace", new Button.ClickListener() {
//            @Override
//            public void buttonClick(Button.ClickEvent event) {
//                UI.getCurrent().getNavigator().navigateTo(WorkspaceView.NAME);
//            }
//        });
//        addComponent(workspaceButton);
        layout.addComponent(contentLayout);
        addComponent(layout);
    }
}

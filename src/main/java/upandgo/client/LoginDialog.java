package upandgo.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Binder;

import upandgo.client.Resources.DialogBoxStyle;
import upandgo.client.Resources.NavBarStyle;

// sol found here: http://stackoverflow.com/questions/6913081/gwt-popup-and-uibinder-panel-or-dialogbox
public class LoginDialog extends DialogBox {
	@UiField
	Label label1;
	
	
	
    private static final Binder binder = GWT.create(Binder.class);
    
    private DialogBoxStyle diaStyle = Resources.INSTANCE.dialogBoxStyle();
	
    interface Binder extends UiBinder<Widget, LoginDialog> {
    }
    
    public LoginDialog() {
        setWidget(binder.createAndBindUi(this));
        setAutoHideEnabled(true);
        setText("My Title");
        setGlassEnabled(true);
        center();
        
        label1.setText("okok");
        
        diaStyle.ensureInjected();
    } 
    
    @UiHandler("okButton")
    void onDismiss(ClickEvent e) {
      hide();
    }
}
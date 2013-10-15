package com.hs18.vaadin.addon;
 
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.hs18.vaadin.addon.graph.GraphJSComponent;
import com.hs18.vaadin.addon.graph.listener.GraphJsLeftClickListener;
import com.vaadin.server.Sizeable;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

/**
 * The Application's "main" class
 */
@SuppressWarnings("serial")
public class MyVaadinUI extends UI
{
	GraphJSComponent graphJSComponent;
	static int increment = 0;
	static String selectedNode = new String();
	
    @Override
    protected void init(VaadinRequest request) {
    	
        final VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        //setContent(layout);
        
    	graphJSComponent = new GraphJSComponent();
    	graphJSComponent.setNodesSize(120, 50);
		graphJSComponent.setLeftClickListener(new GraphJsLeftClickListener() {
			
			@Override
			public void onLeftClick(String id, String type, String parentId) {
				selectedNode = id;
				
				System.out.println(id + " "+ type + " "+ parentId);
				Notification.show("Clicked on node with id = " + id + " at " + type, Notification.Type.WARNING_MESSAGE); 
			}
		});
        graphJSComponent.setImmediate(true);
        
        String lhtml = "<div id='graph' class='graph' ></div>";//add style='overflow:scroll' if required
        Label graphLabel = new Label(lhtml, Label.CONTENT_XHTML);
        
        layout.addComponent(graphLabel);
        layout.addComponent(graphJSComponent);
        
        Button button = new Button("Add new node");
        button.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if (!selectedNode.equals("") && !selectedNode.equals(null)) {
					increment++;
					
					try {
						graphJSComponent.addNode("New node " + increment, "New node " + increment, "level 1", null, selectedNode);
						graphJSComponent.refresh();
						selectedNode = "New node " + increment;
						
						Notification.show("Created new node with id = " + selectedNode); 
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					Notification.show("Please select a node!");
				}
			}
		});
        
        //layout.addComponent(button);
        
        HorizontalSplitPanel hsplit = new HorizontalSplitPanel();
        hsplit.setFirstComponent(button);
        hsplit.setSecondComponent(layout);
        hsplit.setSplitPosition(200, Sizeable.UNITS_PIXELS);
        hsplit.setLocked(true);

        setContent(hsplit);
        
        prepareGraph();
    }

	private void prepareGraph(){
		try {
			graphJSComponent.addNode("fruits", "Fruits I Like", "level 1", null, null);//Give parent id as null for root node
			graphJSComponent.getNodeProperties("fruits").put("title", "Fruits I Like"); 
			graphJSComponent.addNode("watermelon", "Watermelon", "level 2", null, "fruits");//first child of node with id fruits
			graphJSComponent.getNodeProperties("watermelon").put("title", "Its a very juicy fruit."); 
			graphJSComponent.addNode("mango", "Mango", "level 2", null, "fruits");//second child of node with id fruits
			graphJSComponent.getNodeProperties("mango").put("title", "Katrina Kaif's favourite."); 
			graphJSComponent.addNode("apple", "Apple", "level 2", null, "fruits");//third child of node with id fruits
			graphJSComponent.getNodeProperties("apple").put("title", "One apple a day, keeps the doctor away"); 
			graphJSComponent.getNodeProperties("apple").put("fill", "#F00");
			graphJSComponent.addNode("apple accesories", "Apple 2", "level 3", null, "apple");//first child of node with id apple
			graphJSComponent.getNodeProperties("mango").put("fill", "yellow");
			
			graphJSComponent.addNode("5", "Hapoos", "level 3", null, "mango");//child of mango node
			graphJSComponent.getNodeProperties("5").put("title", "One of the best mangos"); 
			
			graphJSComponent.addNode("6", "Green", "level 3", null, "watermelon");//child of watermelon node
			graphJSComponent.getNodeProperties("6").put("title", "Green from outside, red inside"); 
			graphJSComponent.getNodeProperties("6").put("fill", "#0F0");
			
			//Another Tree in the same graph
			graphJSComponent.addNode("fruitsnotlike", "Fruits I Dont Like", "level 1",  null, null);//Give parent id as null
			graphJSComponent.getNodeProperties("fruitsnotlike").put("title", "Another tree in the same graph"); 
			graphJSComponent.addNode("lichy", "Lichy", "level 2", null, "fruitsnotlike");//first child of node with id fruitsnotlike
			graphJSComponent.getNodeProperties("lichy").put("title", "because its nto easy to eat it."); 
			graphJSComponent.addNode("redlichy", "Red Lichy", "level 3", null, "lichy");
			graphJSComponent.getNodeProperties("redlichy").put("title",  "red lichy"); 
			
			graphJSComponent.refresh();//Call refresh after you are done with your changes
		} catch (Exception e) {
			e.printStackTrace();
		}//
	}
}

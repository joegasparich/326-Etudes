package value;

import java.util.*;

/**
 * The class for bargain finders.
 * 
 * @author MichaelAlbert
 */
public class BargainFinder {
    
    private SiteInfo site;
    private CustomerInfo customer;
    private int budget;
    private ArrayList<String> currentCart; //will hold sets of items from the shoppinglist
    private ArrayList<String> bestValueCart; //the best cart found so far
    private int bestCartPrice = 0; //customers value of bestValueCart, for easy reference

    public BargainFinder(SiteInfo site, CustomerInfo customer, int budget) {
        this.site = site;
        this.customer = customer;
        this.budget = budget;
    }
    
    public ArrayList<String> shoppingList() {
        currentCart = new ArrayList<String>(); //inits the cart
        ArrayList<String> itemsList = customer.getItems(); //gets the shooping list of customer
        bestValueCart = new ArrayList<String>();
        dfs(itemsList,currentCart);
        return bestValueCart;
        
    }
    
    
    public void dfs(ArrayList<String> shopList, ArrayList<String> currentCart){
     
        int currentCartCost = site.getCost(currentCart);
        int cartVal = 0;
        System.out.println(currentCart + ", " + currentCartCost);
        //checks cart over budget
        if(currentCartCost > this.budget){
            return;
        }
        //gets current value of cart
        for(String str: currentCart){
            cartVal+=customer.getValue(str);
        }
        //checks if the current cart is the best value and updates
        if(cartVal > bestCartPrice){
            bestValueCart = new ArrayList<String>(currentCart);
            bestCartPrice = cartVal;
        }
        //tries new shopping lists by adding items to the cart in dfs style
        for(int index = 0;index<shopList.size();index++){
                
            ArrayList<String> newList = new ArrayList<String>(shopList);
            String newItem = newList.remove(index);
            
            currentCart.add(newItem);
            
            dfs(newList,currentCart); // will check the next cart with item off list but in cart
            currentCart.remove(newItem);
        }
    }
}

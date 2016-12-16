package co.jlabs.cersei_retailer.custom_components;

/**
 * Created by JussConnect on 7/2/2015.
 */
public class Class_Cart {
    int id;
    public String detail;
    public String retailer_name;
    public String retailer_id;
    public String item_id;
    public String weight;
    public String offer_id;
    public String category_name;
    public String company_name;
    public String product_name;
    public Integer price;
    public Integer cashback;
    public Integer quantity;
    public Integer remaining_qrcodes;
    public String img;

    Class_Cart(){
        detail = new String();
        retailer_name = new String();
        weight= new String();
        price= new Integer("0");
        cashback = new Integer("0");
        remaining_qrcodes = new Integer("0");
        quantity = new Integer("0");
        img= new String();
        retailer_id= new String();
        item_id= new String();
        offer_id= new String();
        category_name= new String();
        company_name= new String();
        product_name= new String();

    }
}

package co.jlabs.cersei_retailer.custom_components;



import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class Sqlite_cart extends SQLiteOpenHelper {

	// Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "Cart";
    Context context;

	public Sqlite_cart(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;

    }

	@Override
	public void onCreate(SQLiteDatabase db) {
 		String CREATE_CartED_TABLE = "CREATE TABLE Cart ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "detail TEXT, " +
                "price INTEGER,"+
                "retailer_name TEXT, " +
				"item_id TEXT, "+
                "img TEXT,"+
                "offer_id TEXT,"+
                "category_name TEXT,"+
                "company_name TEXT,"+
				"cashback INTEGER,"+
                "product_name TEXT,"+
                "weight TEXT,"+
                "quantity INTEGER, " +
                "retailer_id TEXT);";
 		db.execSQL(CREATE_CartED_TABLE);
        String CREATE_RETAILER_LIST = "CREATE TABLE Retailer ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "retailer_name TEXT, " +
                "contact TEXT, " +
                "min_order TEXT, " +
                "address TEXT, " +
                "retailer_id TEXT);";
        db.execSQL(CREATE_RETAILER_LIST);
        String CREATE_RETAILER_AREAS = "CREATE TABLE RetailerArea ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "shipping_charges INTEGER, " +
                "locality TEXT, " +
                "sub_locality TEXT, " +
                "areas TEXT, " +
                "retailer_id TEXT);";
        db.execSQL(CREATE_RETAILER_AREAS);



    }


	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Cart");
        db.execSQL("DROP TABLE IF EXISTS Retailer");
        db.execSQL("DROP TABLE IF EXISTS RetailerArea");

        this.onCreate(db);
	}
	//---------------------------------------------------------------------

	/**
     * CRUD operations (create "add", read "get", update, delete)
     */

    private static final String TABLE_Cart = "Cart";
    private static final String TABLE_Retailer = "Retailer";

    private static final String KEY_ID = "id";
    private static final String KEY_DETAIL = "detail";
    private static final String KEY_PRICE = "price";
    private static final String KEY_REATAILER_NAME = "retailer_name";
    private static final String KEY_ITEM_ID = "item_id";
    private static final String KEY_IMG = "img";
    private static final String KEY_OFFER_ID = "offer_id";
    private static final String KEY_CATEGORY_NAME = "category_name";
    private static final String KEY_COMPANY_NAME = "company_name";
    private static final String KEY_PRODUCT_NAME = "product_name";
    private static final String KEY_WEIGHT = "weight";
    private static final String KEY_RETAILER_ID = "retailer_id";
    private static final String KEY_CASHBACK = "cashback";
    private static final String KEY_QUANTITY = "quantity";
    private static final String KEY_CONTACT = "contact";
    private static final String KEY_MIN_ORDER = "min_order";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_SHIPPING_CHARGES = "shipping_charges";
    private static final String KEY_LOCALITY = "locality";
    private static final String KEY_SUB_LOCALITY = "sub_locality";
    private static final String KEY_AREAS = "areas";








    public int addToCart(Class_Cart tp){

        int quantity = findIfOfferAlreadyExistsInCart(tp.offer_id);

        if(quantity>0)
        {
            UpdateQuantity(tp.offer_id,quantity+1);
            quantity=quantity+1;
        }
        else {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_DETAIL, tp.detail);
            values.put(KEY_PRICE, tp.price);
            values.put(KEY_REATAILER_NAME, tp.retailer_name);
            values.put(KEY_ITEM_ID, tp.item_id);
            values.put(KEY_IMG, tp.img);
            values.put(KEY_OFFER_ID, tp.offer_id);
            values.put(KEY_CATEGORY_NAME, tp.category_name);
            values.put(KEY_COMPANY_NAME, tp.company_name);
            values.put(KEY_PRODUCT_NAME, tp.product_name);
            values.put(KEY_WEIGHT, tp.weight);
            values.put(KEY_RETAILER_ID, tp.retailer_id);
            values.put(KEY_CASHBACK, tp.cashback);
            values.put(KEY_QUANTITY, 1);
            db.insert(TABLE_Cart, null, values);
            values.clear();

            db.close();
            quantity=1;
        }
        return quantity;
    }


    public void addRetailer(Class_retailer cr){


        //int quantity = findIfRetailerExist(cr.retailer_id);



            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_REATAILER_NAME, cr.retailer_name);
            values.put(KEY_RETAILER_ID, cr.retailer_id);
            values.put(KEY_CONTACT, cr.contact);
            values.put(KEY_MIN_ORDER, cr.min_order);
            values.put(KEY_ADDRESS, cr.address);
            db.insert(TABLE_Retailer, null, values);
            values.clear();

            db.close();


    }





    public void addRetailer(JSONObject tp){


        try {

          //  quantity= findIfRetailerExist(tp.getString("retailer_id"));

                Log.e("log",""+tp.toString());
                SQLiteDatabase db = this.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(KEY_REATAILER_NAME, tp.getString("name"));
                values.put(KEY_RETAILER_ID, (tp.getString("retailer_id")) );
                values.put(KEY_CONTACT, tp.getString("contact"));
                values.put(KEY_MIN_ORDER, tp.getString("min_order"));
                values.put(KEY_ADDRESS, tp.getString("address"));
                db.insert(TABLE_Retailer, null, values);
                values.clear();
                db.close();


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }




    public int addToCart(JSONObject tp){

        int quantity =0;

        try {

            quantity= findIfOfferAlreadyExistsInCart(tp.getString("offer_id")+tp.getString("retailer_id"));

            if(quantity>0)
            {
                UpdateQuantity(tp.getString("offer_id")+tp.getString("retailer_id"),quantity+1);
                quantity=quantity+1;
            }
            else {
                SQLiteDatabase db = this.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(KEY_DETAIL, tp.getString("detail"));
                values.put(KEY_PRICE, Integer.parseInt((tp.getString("price"))) );
                values.put(KEY_REATAILER_NAME, tp.getString("retailer_name"));
                values.put(KEY_ITEM_ID, tp.getString("item_id"));
                String imga=tp.getJSONArray("img").getString(0);
                values.put(KEY_IMG, imga);
                values.put(KEY_OFFER_ID, tp.getString("offer_id")+tp.getString("retailer_id"));
                values.put(KEY_CATEGORY_NAME, tp.getString("category_name"));
                values.put(KEY_COMPANY_NAME, tp.getString("company_name"));
                values.put(KEY_PRODUCT_NAME, tp.getString("product_name"));
                values.put(KEY_WEIGHT, tp.getString("weight"));
                values.put(KEY_RETAILER_ID, tp.getString("retailer_id"));
                values.put(KEY_CASHBACK, tp.getInt("cashback"));
                values.put(KEY_QUANTITY, 1);
                db.insert(TABLE_Cart, null, values);
                values.clear();
                db.close();
                quantity=1;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return quantity;
    }

    public ArrayList<Class_Cart> getDistinctRetailer() {
        ArrayList<Class_Cart> Carted = new ArrayList<>();
        String query = "SELECT  DISTINCT  retailer_id  FROM Cart ";
        SQLiteDatabase db = this.getWritableDatabase();
        while(db.inTransaction())
        {

        }
        db.beginTransaction();
        Cursor cursor = db.rawQuery(query, null);
        db.endTransaction();
        Class_Cart tp = null;
        if (cursor.moveToFirst()) {
            do {
                tp = new Class_Cart();
                tp.retailer_id=cursor.getString(0);
                Log.e("somedata",":"+cursor.getString(0));
                Log.e("somedata1",":"+cursor.getColumnName(0));

                Carted.add(tp);
            } while (cursor.moveToNext());
        }



        db.close();




        return Carted;




    }





    public ArrayList<Class_Cart> getRetailerOrderData(String s) {
        ArrayList<Class_Cart> Carted = new ArrayList<>();
        String query = "SELECT  *   FROM Cart where retailer_id = "+"'"+s+"'" ;
        SQLiteDatabase db = this.getWritableDatabase();
        while(db.inTransaction())
        {

        }
        db.beginTransaction();
        Cursor cursor = db.rawQuery(query, null);
        db.endTransaction();
        Class_Cart tp = null;
        if (cursor.moveToFirst()) {
            do {
                tp = new Class_Cart();
                tp.id=(Integer.parseInt(cursor.getString(0)));
                tp.detail =(cursor.getString(1));
                tp.price =(Integer.parseInt(cursor.getString(2)));
                tp.retailer_name=cursor.getString(3);
                tp.item_id=cursor.getString(4);
                tp.img=cursor.getString(5);
                tp.offer_id=cursor.getString(6);
                tp.category_name=cursor.getString(7);
                tp.company_name=cursor.getString(8);
                tp.cashback=(Integer.parseInt(cursor.getString(9)));
                tp.product_name=cursor.getString(10);
                tp.weight=cursor.getString(11);
                tp.quantity=(Integer.parseInt(cursor.getString(12)));
                tp.retailer_id=cursor.getString(13);
                Log.e("somedata",":"+cursor.getColumnName(0));
                Log.e("somedata",":"+cursor.getString(1));
                Carted.add(tp);
            } while (cursor.moveToNext());
        }



        db.close();




        return Carted;




    }




    public ArrayList<Class_retailer> getRetailerData(String s) {
        ArrayList<Class_retailer> Carted = new ArrayList<>();
        String query = "SELECT  *   FROM Retailer where retailer_id = "+"'"+s+"'" ;
        SQLiteDatabase db = this.getWritableDatabase();
        while(db.inTransaction())
        {

        }
        db.beginTransaction();
        Cursor cursor = db.rawQuery(query, null);
        db.endTransaction();
        Class_retailer tp = null;
        if (cursor.moveToFirst()) {
            do {
                tp = new Class_retailer();
                tp.id=(Integer.parseInt(cursor.getString(0)));
                tp.retailer_name =cursor.getString(1);
                tp.contact =cursor.getString(2);
                tp.min_order=cursor.getString(3);
                tp.address=cursor.getString(4);
                tp.retailer_id=cursor.getString(5);
                Log.e("somedatar",":"+cursor.getColumnName(0));
                Log.e("somedatare",":"+cursor.getString(1));
                Carted.add(tp);
            } while (cursor.moveToNext());
        }
        db.close();




        return Carted;




    }





    public ArrayList<Class_Cart> getAllCart() {
    	ArrayList<Class_Cart> Carted = new ArrayList<>();
        String query = "SELECT  * FROM " + TABLE_Cart + " ORDER BY id desc";
        SQLiteDatabase db = this.getWritableDatabase();
        while(db.inTransaction())
        {
        	
        }
        db.beginTransaction();
        Cursor cursor = db.rawQuery(query, null);
        db.endTransaction();
        Class_Cart tp = null;
        if (cursor.moveToFirst()) {
            do {
            	tp = new Class_Cart();
                tp.id=(Integer.parseInt(cursor.getString(0)));
            	tp.detail =(cursor.getString(1));
                tp.price =(Integer.parseInt(cursor.getString(2)));
                tp.retailer_name=cursor.getString(3);
                tp.item_id=cursor.getString(4);
                tp.img=cursor.getString(5);
                tp.offer_id=cursor.getString(6);
                tp.category_name=cursor.getString(7);
                tp.company_name=cursor.getString(8);
                tp.cashback=(Integer.parseInt(cursor.getString(9)));
                tp.product_name=cursor.getString(10);
                tp.weight=cursor.getString(11);
                tp.quantity=(Integer.parseInt(cursor.getString(12)));
                tp.retailer_id=cursor.getString(13);
                Carted.add(tp);
            } while (cursor.moveToNext());
        }
        db.close();
        return Carted;
    }

    public int removeFromCart(String Offer_id)
    {
        int quantity = findIfOfferAlreadyExistsInCart(Offer_id);
        if(quantity>1)
        {
            UpdateQuantity(Offer_id,quantity-1);
            quantity=quantity-1;
        }
        else
        {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_Cart, KEY_OFFER_ID + " = ?", new String[]{String.valueOf(Offer_id)});
            db.close();
            quantity=0;
        }
        return quantity;
    }

    public int deleteFromCart(String Offer_id)
    {
        int quantity = findIfOfferAlreadyExistsInCart(Offer_id);
        if(quantity>=1)
        {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_Cart, KEY_OFFER_ID + " = ?", new String[]{String.valueOf(Offer_id)});
            db.close();
        }
        return quantity;
    }

    public int findIfOfferAlreadyExistsInCart(String offer_id)
    {
        int quantity=0;
        String query = "SELECT  "+KEY_QUANTITY+" FROM " + TABLE_Cart + " WHERE "+KEY_OFFER_ID+" = '"+offer_id+"'";
        SQLiteDatabase db = this.getWritableDatabase();
        while(db.inTransaction())
        {

        }
        db.beginTransaction();
        Cursor cursor = db.rawQuery(query, null);
        db.endTransaction();
        if (cursor.moveToFirst()) {
            quantity=Integer.parseInt(cursor.getString(0));
        }
        db.close();
        Log.e("quan"+query,"::"+quantity);
        return quantity;
    }


    public ArrayList<Class_Cart> findIfRetailerExist()
    {
        ArrayList<Class_Cart> Carted = new ArrayList<>();
        int quantity=0;
        String query = "SELECT DISTINCT  "+KEY_RETAILER_ID+" FROM " + " TABLE_Retailer ";
        SQLiteDatabase db = this.getWritableDatabase();
        while(db.inTransaction())
        {

        }
        db.beginTransaction();
        Cursor cursor = db.rawQuery(query, null);
        db.endTransaction();
        if (cursor.moveToFirst()) {
            quantity=Integer.parseInt(cursor.getString(0));
        }
        db.close();
        Log.e("quan"+query,"::"+quantity);
        return Carted;
    }





    public void UpdateQuantity(String offer_id,int quantity){
        //String query = "UPDATE "+TABLE_Notification+" set "+KEY_Seen+"='0' WHERE "+KEY_ID+">=0";
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_QUANTITY, quantity);
        while(db.inTransaction())
        {

        }
        db.update(TABLE_Cart, values, KEY_OFFER_ID + " = " + "'"+offer_id+"'", null);
        db.close();
    }

    public void UpdateRQuantity(String retailer_id,int quantity){
        //String query = "UPDATE "+TABLE_Notification+" set "+KEY_Seen+"='0' WHERE "+KEY_ID+">=0";
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_QUANTITY, quantity);
        while(db.inTransaction())
        {

        }
        db.update(TABLE_Retailer, values, KEY_RETAILER_ID + " = " + "'"+retailer_id+"'", null);
        db.close();
    }


/*
    public String getCartCode(String cID) {
        String rcode="";
        String query = "SELECT  "+KEY_RCODE+" FROM " + TABLE_Cart + " WHERE "+KEY_CID+" = '"+cID+"'";
        SQLiteDatabase db = this.getWritableDatabase();
        while(db.inTransaction())
        {

        }
        db.beginTransaction();
        Cursor cursor = db.rawQuery(query, null);
        db.endTransaction();
        if (cursor.moveToFirst()) {
            rcode=cursor.getString(0);
        }

        if(rcode.equals(""))
        {
            String qry = "SELECT  count("+KEY_RCODE+") FROM " + TABLE_Cart ;
            while(db.inTransaction())
            {

            }
            db.beginTransaction();
            cursor = db.rawQuery(qry, null);
            db.endTransaction();
            if (cursor.moveToFirst()) {
                rcode=cursor.getString(0);
                if(Integer.parseInt(rcode)>=2) {
                }
                else
                {
                    rcode="";
                }
            }
        }

        db.close();
        return rcode;
    }


    public void addSaved(Class_Cart tp){
        if(getSavedCode(tp.cID).equals("")) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_VID, tp.v_id);
            values.put(KEY_VNAME, tp.v_name);
            values.put(KEY_ADDR, tp.addr);
            values.put(KEY_DEAL, tp.deal);
            values.put(KEY_CID, tp.cID);
            values.put(KEY_M_TYPE, tp.M_Type);
            values.put(KEY_G_TYPE, tp.G_TYPE);
            values.put(KEY_VALID_DATE, tp.valid_date);
            values.put(KEY_Cart_DATE, tp.Cart_date);
            db.insert(TABLE_Saved, null, values);
            values.clear();

            db.close();
        }
    }

    public ArrayList<Class_Cart> getAllSaved() {
        ArrayList<Class_Cart> Carted = new ArrayList<>();
        String query = "SELECT  * FROM " + TABLE_Saved + " ORDER BY id desc";
        SQLiteDatabase db = this.getWritableDatabase();
        while(db.inTransaction())
        {

        }
        db.beginTransaction();
        Cursor cursor = db.rawQuery(query, null);
        db.endTransaction();
        Class_Cart tp = null;
        if (cursor.moveToFirst()) {
            do {
                tp = new Class_Cart();
                tp.id=(Integer.parseInt(cursor.getString(0)));
                tp.v_id=(Integer.parseInt(cursor.getString(1)));
                tp.v_name=cursor.getString(2);
                tp.addr=cursor.getString(3);
                tp.deal=cursor.getString(4);
                tp.cID=cursor.getString(5);
                tp.M_Type=cursor.getString(6);
                tp.G_TYPE=cursor.getString(7);
                tp.valid_date=cursor.getString(8);
                tp.Cart_date=cursor.getString(9);
                Carted.add(tp);
            } while (cursor.moveToNext());
        }
        db.close();
        return Carted;
    }

    public String getSavedCode(String cID) {
        String rcode="";
        String query = "SELECT  "+KEY_ID+" FROM " + TABLE_Saved + " WHERE "+KEY_CID+" = '"+cID+"'";
        SQLiteDatabase db = this.getWritableDatabase();
        while(db.inTransaction())
        {

        }
        db.beginTransaction();
        Cursor cursor = db.rawQuery(query, null);
        db.endTransaction();
        if (cursor.moveToFirst()) {
            rcode=cursor.getString(0);
        }
        db.close();
        return rcode;
    }

    public void deleteSavedCode(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_Saved, KEY_CID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }


    public void addNotification(Class_Cart tp){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_VID, tp.v_id);
        values.put(KEY_M_TYPE, tp.M_Type);
        values.put(KEY_VNAME, tp.v_name);
        values.put(KEY_ADDR, tp.addr);
        values.put(KEY_DEAL, tp.deal);
        values.put(KEY_CID, tp.cID);
        values.put(KEY_Seen, 1);
        values.put(KEY_VALID_DATE, tp.valid_date);
        values.put(KEY_Cart_DATE, tp.Cart_date);
        db.insert(TABLE_Notification,null,values);
        values.clear();

        db.close();
    }

    public ArrayList<Class_Cart> getAllNotification(int n) {
        ArrayList<Class_Cart> Carted = new ArrayList<>();
        String query = "SELECT  id,"+KEY_VID+","+KEY_M_TYPE+","+KEY_VNAME+","+KEY_ADDR+","+KEY_DEAL+","+KEY_CID+","+KEY_VALID_DATE+","+KEY_Cart_DATE+","+KEY_VALID_DATE+"-'"+(new Date().getTime()/1000L)+"' as now FROM " + TABLE_Notification + " where now < 9 ORDER BY id desc";

        if(n>0)
        {
            query=query+" LIMIT "+n;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        while(db.inTransaction())
        {

        }
        db.beginTransaction();
        Cursor cursor = db.rawQuery(query, null);
        db.endTransaction();
        Class_Cart tp = null;
        if (cursor.moveToFirst()) {
            do {
                tp = new Class_Cart();
                tp.id=(Integer.parseInt(cursor.getString(0)));
                tp.v_id=(Integer.parseInt(cursor.getString(1)));
                tp.M_Type=cursor.getString(2);
                tp.v_name=cursor.getString(3);
                tp.addr=cursor.getString(4);
                tp.deal=cursor.getString(5);
                tp.cID=cursor.getString(6);
                tp.valid_date="";
                tp.Cart_date=cursor.getString(8);
                Carted.add(tp);
            } while (cursor.moveToNext());
        }
        db.close();
        return Carted;
    }

    public String getAllNotificationNumber() {
        //String query = "SELECT  * FROM " + TABLE_Notification + " where "+KEY_VALID_DATE+">'"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"' ORDER BY id desc";
        String query = "SELECT  count(id),"+KEY_VALID_DATE+"-'"+(new Date().getTime()/1000L)+"' as now FROM " + TABLE_Notification + " where now < 9 and "+KEY_Seen+">0 ORDER BY id desc";
        String notif="0";
        SQLiteDatabase db = this.getWritableDatabase();
        while(db.inTransaction())
        {

        }
        db.beginTransaction();
        Cursor cursor = db.rawQuery(query, null);
        db.endTransaction();
        if (cursor.moveToFirst()) {
            notif=cursor.getString(0);
        }
        db.close();
        return notif;
    }
public String getSavedNotification(String cID) {
        String rcode="";
        String query = "SELECT  "+KEY_ID+" FROM " + TABLE_Notification + " WHERE "+KEY_CID+" = '"+cID+"'";
        SQLiteDatabase db = this.getWritableDatabase();
        while(db.inTransaction())
        {

        }
        db.beginTransaction();
        Cursor cursor = db.rawQuery(query, null);
        db.endTransaction();
        if (cursor.moveToFirst()) {
            rcode=cursor.getString(0);
        }
        db.close();
        return rcode;
    }*/


}

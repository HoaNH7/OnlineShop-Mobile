package com.example.projectprm.Helper;

import android.content.Context;
import android.widget.Toast;

import com.example.projectprm.Models.PopularProduct;
import com.example.projectprm.Models.Product;

import java.util.ArrayList;

public class ManagmentCart {
    private Context context;
    private TinyDB tinyDB;

    public ManagmentCart(Context context) {
        this.context = context;
        this.tinyDB=new TinyDB(context);
    }

//    public void insertFood(PopularProduct item) {
//        ArrayList<PopularProduct> listpop = getListCart();
//        boolean existAlready = false;
//        int n = 0;
//        for (int i = 0; i < listpop.size(); i++) {
//            if (listpop.get(i).getTitle().equals(item.getTitle())) {
//                existAlready = true;
//                n = i;
//                break;
//            }
//        }
//        if(existAlready){
//            listpop.get(n).setNumberInCart(item.getNumberInCart());
//        }else{
//            listpop.add(item);
//        }
//        tinyDB.putListObject("CartList1",listpop);
//        Toast.makeText(context, "Added to your Cart", Toast.LENGTH_SHORT).show();
//    }
    public void insertFood1(Product item) {
        ArrayList<Product> listpop1 = getListCart1();
        boolean existAlready = false;
        int n = 0;
        for (int i = 0; i < listpop1.size(); i++) {
            if (listpop1.get(i).getTittle().equals(item.getTittle())) {
                existAlready = true;
                n = i;
                break;
            }
        }
        if(existAlready){
            listpop1.get(n).setNumberInCart(item.getNumberInCart());
        }else{
            listpop1.add(item);
        }
        tinyDB.putListObject1("CartList",listpop1);
        Toast.makeText(context, "Added to your Cart", Toast.LENGTH_SHORT).show();
    }

//    public ArrayList<PopularProduct> getListCart() {
//        return tinyDB.getListObject("CartList1");
//    }
    public ArrayList<Product> getListCart1() {
        return tinyDB.getListObject1("CartList");
    }

    public Double getTotalFee1(){
        ArrayList<Product> listItem=getListCart1();
        double fee=0;
        for (int i = 0; i < listItem.size(); i++) {
            fee=fee+(listItem.get(i).getPrice()*listItem.get(i).getNumberInCart());
        }
        return fee;
    }
//    public void minusNumberItem(ArrayList<PopularProduct> listItem,int position,ChangeNumberItemsListener changeNumberItemsListener){
//        if(listItem.get(position).getNumberInCart()==1){
//            listItem.remove(position);
//        }else{
//            listItem.get(position).setNumberInCart(listItem.get(position).getNumberInCart()-1);
//        }
//        tinyDB.putListObject("CartList1",listItem);
//        changeNumberItemsListener.change();
//    }
//    public  void plusNumberItem(ArrayList<PopularProduct> listItem,int position,ChangeNumberItemsListener changeNumberItemsListener){
//        listItem.get(position).setNumberInCart(listItem.get(position).getNumberInCart()+1);
//        tinyDB.putListObject("CartList1",listItem);
//        changeNumberItemsListener.change();
//    }
    public void minusNumberItem1(ArrayList<Product> listItem,int position,ChangeNumberItemsListener changeNumberItemsListener){
        if(listItem.get(position).getNumberInCart()==1){
            listItem.remove(position);
        }else{
            listItem.get(position).setNumberInCart(listItem.get(position).getNumberInCart()-1);
        }
        tinyDB.putListObject1("CartList",listItem);
        changeNumberItemsListener.change();
    }
    public  void plusNumberItem1(ArrayList<Product> listItem,int position,ChangeNumberItemsListener changeNumberItemsListener){
        listItem.get(position).setNumberInCart(listItem.get(position).getNumberInCart()+1);
        tinyDB.putListObject1("CartList",listItem);
        changeNumberItemsListener.change();
    }

}

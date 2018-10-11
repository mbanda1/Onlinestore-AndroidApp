package com.cyphertech.biashara.cart;

import com.cyphertech.biashara.product.Album;

import java.util.ArrayList;

public class cartArray {

    private static ArrayList<Album> cartListImageUri = new ArrayList<>();


    private static boolean contains(ArrayList <Album> list, String img) {
        for (Album item : list) {
            if (item.getProductImage().equals(img)) {
                return true;
            }
        }
        return false;
    }


    // Methods for Cart
    @SuppressWarnings("UnusedReturnValue")
    public static boolean addCartListImageUri(String b_id, String b_cat, String wishBrandName, String wishlistImageUri,
                                              Double wishBrandPrice, String wishBrandDesc, String wishBrandSpec, int quantity,
                                              Double wishPrice) {

         //boolean success = false;
            if (!contains(cartListImageUri, wishlistImageUri)) {
              //  grandTotal(cartListImageUri);

                Album developers = new Album(b_id, b_cat, wishBrandName, wishlistImageUri, wishBrandPrice, wishBrandDesc,
                        wishBrandSpec, quantity, wishPrice);
                cartListImageUri.add(developers);

                //addPrice(wishBrandPrice);


               return true;
        } else {
           //     Toast.makeText(,"Alredy Added.",Toast.LENGTH_SHORT).show();
                return false;
         }

        // return success;
    }




    public void removeCartListImageUri(int position) { cartListImageUri.remove(position); }
    public static ArrayList<Album> getCartListImageUri(){ return cartListImageUri; }
    public static void clearCart() {
        cartListImageUri.clear();
    }






   // price
    public static Double grandTotal(){

        Double totalPrice = 0.00;

        for(int i = 0 ; i < cartListImageUri.size(); i++) {
            totalPrice += cartListImageUri.get(i).getFinalPrice();
         }

        return totalPrice;
    }


     /**************************************************/
    public static void addCartListImageUri2(int position, String p_id, String p_cat, String wishBrandName, String wishlistImageUri,
                                            Double wishBrandPrice, String wishBrandDesc, String wishBrandSpec, int quantity) {


        if (contains(cartListImageUri, wishlistImageUri)) {
            Double totalPerItem = quantity * wishBrandPrice;


            Album developers = new Album(p_id, p_cat, wishBrandName, wishlistImageUri, wishBrandPrice, wishBrandDesc, wishBrandSpec,
                    quantity, totalPerItem);

            cartListImageUri.set(position, developers);

        }

    }



}


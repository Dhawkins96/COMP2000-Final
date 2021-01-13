# COMP2000-Final
Final COMP2000 assignment

I have created a simple java shopping kiosk application for my COMP2000 assignment

![Kiosk Main](https://user-images.githubusercontent.com/57950733/104505029-3e847900-55db-11eb-90d8-5beb26f8e374.png)

As you can see from the above photo this is my Kiosk's main page. Here if you a customer you can find your item you would like to buy and add it to the basket below. A total of all the items added to the basket is also added up in a txt box at the side.

![Adding to Basket](https://user-images.githubusercontent.com/57950733/104505037-3f1d0f80-55db-11eb-8ac4-57d46f57cfb4.png)

Once the customer has finished adding all their items they can selected card/cash which will take them to a page where they can pay for said items. 

![Card Payment Main](https://user-images.githubusercontent.com/57950733/104505041-3fb5a600-55db-11eb-8915-3806fad9f68a.png)
![Cash Payment Main](https://user-images.githubusercontent.com/57950733/104505049-40e6d300-55db-11eb-9367-605059d17d28.png)

As you can see the baskets from both orders have been added to the list box and the total is present at the side. With the card method the customer will only need to click pay and see if they have been approved by their bank. 

![Card Payment Approved](https://user-images.githubusercontent.com/57950733/104505040-3fb5a600-55db-11eb-8885-84fec0edab9f.png)

Once the card has been approved the customer can the print out their receipt which will open in both the JOptionPane and a text area on the GUI.

![Card Receipt TextArea](https://user-images.githubusercontent.com/57950733/104505043-3fb5a600-55db-11eb-866e-27ee733815d3.png)
![Card Receipt](https://user-images.githubusercontent.com/57950733/104505044-404e3c80-55db-11eb-86a9-c3055a6500a3.png)

In the cash method the customer will have to input the amount they would like to pay and if they dont input enough they won't be able to get their receipt. Once they have input enough they will get back any change given and will be able to get their receipt in the same format as the card.

![Cash More Money](https://user-images.githubusercontent.com/57950733/104505047-404e3c80-55db-11eb-9d30-b57797d88ac8.png)
![Cash Payment Change](https://user-images.githubusercontent.com/57950733/104505048-40e6d300-55db-11eb-99fd-88967aac5582.png)

At the start of this application, you can see an Admin Login button. Here if the admin need to login they just enter their personal log on and are taken to a admin main page. If the admin inputs an incorrect password or username they will be prompted to re-enter the details.

![UserPass Error](https://user-images.githubusercontent.com/57950733/104505035-3f1d0f80-55db-11eb-88e0-a6540c9ac27f.png)
![Admin Main](https://user-images.githubusercontent.com/57950733/104505038-3f1d0f80-55db-11eb-8bf0-801b0894b4ce.png)

As you can see from here the admin can add, edit and remove stock items from the database. There is also a reorder list box which will highlight any items which have less than 5 items in stock. Letting the admin know they need to reorder.



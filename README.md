# Hungry-Foodie
## Team Members
- Tianli Wu
- Mengchen Gong

## Instruction
- Code for this application in `app` folder
- To run the program: 
  - Clone or download
  - Open android studio
  - Click the open button
  - Select the file which has our code
  - under 'Build' tab press 'Build Project'
  - under 'Run' tab press 'Run Main'follow the prompt menu

## Patterns
- Strategy

  The usage of Strategy pattern is in user types, specifically different customer types, which are Customers and VIPCustomers. For VIP customers, all orders are Free to deliver. For normal Customers, if the order if under 25$, they need to pay 5$ for delivery fee on top of the food cost
- Decorator

  Order total cost calculation with order adds-on calculation used Decorator Pattern. For each meal, the cost has been calculated and then if there is more things added (meat or cheese or both) by certain customer, the total price will be increment by using decorator pattern
Observer
No matter customers choose takeout or delivery the order, they will be notified for cooking status or delivery status respectively. We have 2 observers here in the program takeOutObserver and DeliveryObserver. For difference choices customers chose, they will be notified differently
- Adapter
  
  Adapter pattern used here is between the view and the list of items we have in the shopping cart. We use this adapter to provide an AdapterView, returning a view for each object in a collection of data objects that we provide. 
- Command
   
   We used a Command pattern between Customer and Merchant. DeliveryMan in our app works as an Invoker that takes the prepped order from Merchant and delivers the food to customers. To be specific, between customer and merchant, there are delivery man and app, DeliveryMan would be notified by Merchant, and work as our invoker (w/ the usage of the app) to execute the order. 
- Abstract Factory

  Abstract Factory pattern is used to produce meals. A normal meal contains 1 entree and 1 side for all restaurants’ (merchants’) menu. Burger meal for example, the combination is: Burger (entree), Pepsi (side). When a client clicks an image button in the Menu page, the meal name will be sent to the check out page. Pass the meal name to the Abstract factory pattern to create a meal object. So if a customer wants to put a meal in the shopping cart, the process is actually to use the Abstract Factory pattern to produce the meal and add it to the corresponding customer’s cart list. 

## App interface
- General 
  - Register
- Customers + VIPCustomers
  - Login
  - Login pop window wrong type message
  - Restaurant list
  - Restaurant’s menu (2)
  - Shopping cart
  - Add-on 
  - Meal info  (2)
  - Checkout
  - Take-out & delivery option
  - Order summary (review)
  - Takeout status
  - Delivery status
- Merchants (Restaurants)
  - Merchant log-in
  - Merchant Main
  - Merchant menu
  - Merchant edit (pop window)
  - Merchant order list (notify deliveryMan)
  - Merchant summary 
  - Merchant delivery update (pop window) 
- DeliveryMan 
  - DeliveryMan login
  - DeliveryMan order notification (pick up order)
  - DeliveryMan finish delivering
  - Merchant summary 
  - Merchant delivery update




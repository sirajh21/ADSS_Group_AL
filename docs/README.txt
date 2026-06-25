Assignment 2 – Suppliers & Inventory Integration

Group Members Name+ID:
212704548 Hamza Dabouri 
214641326 Siraj Hoshan
215691205 Loai Shaaban
326320421 Roaa Aldada
213503212 Lilyan Hannot
Description:
This project integrates the Suppliers Management module with the Inventory Management module.
The system supports supplier management, contract management, inventory management, low stock alerts,
automatic supplier orders due to shortages, periodic supplier orders, and SQLite database persistence.

The system works as one unified system. Both modules use the same local SQLite database.

Files Description:

Main.java
Starts the application and allows the user to choose between the Stock module and the Supplier module.

Stockmain.java
Handles the Stock module menu and stock-related user interaction.

MainController.java
Handles the Supplier module main menu and connects the supplier service to the application.

SupplierController.java
Manages suppliers, supplier products, contracts, supplier contacts, and supplier searches.

OrderController.java
Manages regular supplier orders and periodic supplier orders.
It creates automatic shortage orders, updates periodic orders, prevents duplicate shortage orders,
and selects the best supplier according to the contract price.

productscontroller.java
Manages inventory products and low stock alerts.
When a product is below its minimum amount, it sends the shortage information to the order controller.

DataBaseController.java
Handles the connection between the business logic and the SQLite database.

SuppliersDAO.java
Handles database operations for suppliers.

Product1DAO.java
Handles database operations for supplier products.

OrderDAO.java
Handles database operations for supplier orders.

ContractDAO.java
Handles database operations for supplier contracts.

ContactsDAO.java
Handles database operations for supplier contacts.

Product.java
Represents an inventory product in the stock module.

Product1.java
Represents a supplier product in the suppliers module.

Supplier.java
Represents a supplier and stores supplier details, products, contracts, and contacts.

Contract.java
Represents a supplier contract and supports price calculation according to discounts.

Order.java
Represents a supplier order.

PeriodicOrder.java
Represents a periodic supplier order.

Database:
The project uses a local SQLite database.

Database file location:
dev/superli.db

The database contains sample data that allows testing the main functionality of the system.

Documentation Files:

docs/class-diagram.pdf
Updated integrated class diagram in PDF format.

docs/class-diagram.xml
Editable source file of the integrated class diagram.

docs/requirements.pdf
Updated requirements document for the integrated system.

docs/contracts/contract-AddOrder.pdf
Operation contract for creating a supplier order due to an inventory shortage.

docs/contracts/contract-AddPeriodicOrder.pdf
Operation contract for creating a periodic supplier order.

docs/sequence-diagrams/seq-diagram-addOrder.pdf
Sequence diagram for creating a supplier order due to an inventory shortage.

docs/sequence-diagrams/seq-diagram-addOrder.xml
Editable source file of the shortage-order sequence diagram.

docs/sequence-diagrams/seq-diagram-addPeriodicOrder.pdf
Sequence diagram for creating a periodic supplier order.

docs/sequence-diagrams/seq-diagram-addPeriodicOrder.xml
Editable source file of the periodic-order sequence diagram.

docs/use-cases/use-case-diagram.pdf
Use-case diagram for the integrated system.

docs/use-cases/use-case-diagram.xml
Editable source file of the use-case diagram.

docs/use-cases/use-case-e.pdf
Detailed use case for creating a periodic supplier order.

docs/use-cases/use-case-f.pdf
Detailed use case for creating a supplier order due to an inventory shortage.

Project Structure:

docs/
Contains the requirements, use cases, contracts, class diagram, sequence diagrams, and README file.

dev/
Contains the source code, tests, previous assignment code, and the SQLite database.

release/
Contains the executable file:

adss2025_v02.jar

Main database tables:

* Supplier
* Contacts
* Supplier_Contact
* Product
* Contract
* Order1
* products
* items
* Categories
* Report

Initial Sample Data:
The database includes several suppliers and products, including:

Suppliers:

* StraussSupplier
* TnuvaSupplier
* OsemSupplier

Supplier products:

* Milk, Product ID 1
* Bread, Product ID 2
* Tuna, Product ID 3

Inventory products:

* Milk, Product ID 1

Important existing orders:

* Shortage order for Milk from TnuvaSupplier
* Periodic order for Tuna from OsemSupplier

Main Functionality:

1. Automatic order due to inventory shortage
   When the stock module detects that a product quantity is below the minimum amount,
   the system creates an automatic supplier order.

2. Choosing the best supplier
   If more than one supplier can supply the same product, the system checks the supplier contracts
   and chooses the supplier with the best price for the required quantity.

3. Duplicate order prevention
   If an order already exists for a shortage product, the system does not create a duplicate order.

4. Periodic supplier orders
   The system supports creating and updating periodic supplier orders.

5. Database persistence
   All important data is saved locally in the SQLite database.
   Orders, suppliers, contracts, products, and stock data remain available after restarting the program.

How to Run:

1. Open a terminal from the main project folder.
2. Run the following command:

java -jar release/adss2025_v02.jar

3. The system will display:

1 stock , 2 supplier, 0 exit

4. Choose the required module and follow the menu options.

Manual Test Scenarios:

Test 1 – Show periodic supplier order:
Run the program and choose:

2
5
6
3

Expected result:
Order num<200>
Supplier Name: OsemSupplier
Tuna(ID:3), Quantity:35, TotalCost:$498.75

Test 2 – Stock alert and duplicate order prevention:
Run the program and choose:

1
11

Expected result:
Supplier data loaded from DB.
Stock data loaded from DB.
Product ID: 1 is almost out of stock current amount: 10
Order already exists for product ID: 1 in order ID: 3

Tests:
The project includes unit and integration tests under the tests folder.
The tests cover database loading, suppliers, products, order creation, supplier selection,
duplicate order prevention, periodic orders, order updates, and price calculation.

Technologies and Libraries Used:

* Java
* SQLite
* sqlite-jdbc
* IntelliJ IDEA
* Command Line Interface

Notes:

* The SQLite JDBC driver is included inside the JAR file.
* No external library installation is required to run the JAR.
* The JAR should be run from the main project folder so it can access dev/superli.db.
* Do not press Initialize Data repeatedly, because it may insert duplicate sample data.
* The database file dev/superli.db must be submitted with the project.


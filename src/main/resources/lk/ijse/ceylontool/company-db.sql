
Create database CEYLON_TOOL;


 Use CEYLON_TOOL;

Create table Customer(
                         id           varchar(7)       primary key,
                         name     varchar(20),
                         address varchar(30)

);

create table orders(
                       order_id varchar(35) primary key,
                       cus_id varchar(7) not null,
                       date date not null,
                       constraint foreign key (cus_id) references customer(id)
                           on delete cascade on update cascade
);


Create table Delivery_cost(

                              Dc_id    varchar(5) primary key,
                              Dc_weight_range varchar(20),
                              Dc_amount    decimal(10,2)
);



Create table Payment(

                        Payment_id varchar(5) primary key,
                        Payment_amount  decimal(10,2),
                        Payment_date date
);









Create table Supplier(

                         id varchar(10) primary key,
                         name varchar(10),
                         address varchar(20),
                         tp  int(10)
);



Create table 	Item_Supply(

                               t_id varchar(12) primary key,
                               code varchar(15) ,

                               s_id      varchar(10)  ,

                               sup_name   varchar(25),
                               item_name varchar(25),

                               qty int(5),
                               buyingPrice  decimal(10,2)

);

Create table Packing_material(

                                 Pm_id varchar(5) primary key,
                                 Description varchar(20),
                                 Price_per_unith    decimal(10,2),
                                 Qty_on_hand   decimal(10,2)
);



Create table Facebook_pages_add(
                                   Add_id varchar(5) primary key,
                                   Add_date date,
                                   Add_amount decimal(10,2),
                                   For_days  int(3)
);




Create table Facebook_page_admin(
                                    Fbpage_admin_id varchar(5) primary key,
                                    Name varchar(20),
                                    Address varchar(30),
                                    Mobile_number int(10),
                                    Account_details varchar(70),
                                    Salary_for_per_order_in_fbpage    decimal(10,2),

                                    Salary_for_per_order_in_marketplace    decimal(10,2),
                                    Salary_balance  decimal(10,2)
);





Create table Item(
                     code varchar(15) primary key,
                     description varchar(30),
                     unitPrice    decimal(10,2),
                     qtyOnHand  int(5),
                     sellingPrice decimal(10,2),
                     weight varchar(100)

);



Create table Item_packing_material_details(
                                              Pm_id varchar(5),
                                              code varchar(5),
                                              Needed_qty   int(3),
                                              Foreign key(code ) references  Item( code) on update cascade on delete cascade ,
                                              Foreign key(pm_id ) references  Packing_material( pm_id) on update cascade on delete cascade
);









create table order_detail(
                             order_id varchar(35) not null ,
                             item_code varchar(15) not null,
                             qty int not null,
                             unit_price double not null,
                             constraint foreign key (order_id) references orders(order_id)
                                 on delete  cascade on update cascade,
                             constraint foreign key (item_code) references item(code)
                                 on delete cascade on update cascade
);







//         most moving item  //

    Select  i.description as bad_item from  Item i join  order_detail od on i.code = od.item_code group by  i.code, i.description order by sum(od.qty) desc limit 1;


// least moving itemm //

         Select  i.description as bad_item from  Item i join  order_detail od on i.code = od.item_code group by  i.code, i.description order by sum(od.qty) asc limit 1;

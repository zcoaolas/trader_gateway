## Trader Gateway API

/TraderUser/Login POST 
---
> Authenticate the user.

*request*
```
{
  "tu_name": "zhanchen",
  "tu_password": "zhanchen"
}
```

*success response*
```
{
  "tu_id": 20,
  "tu_name": "zhanchen",
  "tu_role": 2,
  "tu_email": "zhanchen@qq.com",
  "t_id": 0,
  "tu_password": "",
  "tu_hash": -170952320
}
``` 
  
*failed response* 
``` 
{
  "message": "Log in Failed"
}
```

> HEADER after login:
```
"TU-Hash" -> 5704312
"TU-Name" -> "zhanchen"
```

/UserOrder POST
---
> A UserOrder is submitted to the trader.

> uo_type: "Stop" "Limit" "Cancel" "Market"  
> uo_status: "Placed" "Cancelled" "PartiallyBought" "PartiallyFinished" "Finished"  
> uo_time_limit: This order must be finished in XXX milliseconds  
> uo_max_order_vol: The maximum volume of the split order  


*request body*
```
{
  "c_id": 2,
  "uo_type": "Market",
  "uo_vol": 350,
  "uo_year": 2018,
  "uo_month": 10,
  "uo_is_buy": 1,
  "uo_limit_value": "",
  "uo_stop_value": "",
  "uo_time_limit": 0,
  "uo_max_order_vol": 90
}
``` 
```
{
  "c_id": 2,
  "uo_type": "Limit",
  "uo_vol": 350,
  "uo_year": 2018,
  "uo_month": 10,
  "uo_is_buy": 1,
  "uo_limit_value": "3.7%",
  "uo_stop_value": "",
  "uo_time_limit": 1500000,
  "uo_max_order_vol": 150
}
```
```
{
    "c_id": 2,
    "uo_type": "Stop",
    "uo_vol": 350,
    "uo_year": 2018,
    "uo_month": 10,
    "uo_is_buy": 0,
    "uo_limit_value": "",
    "uo_stop_value": "229.6",
    "uo_time_limit": 30000,
    "uo_max_order_vol": 90
}
```

*success response*
```
{
    "msg": "Success"
}
```

*failed response*
```
401 Unauthorized
```
```
{
  "message": "Add UserOrder Failed"
}
```

/UserOrder/All GET
---
> Get all orders a traderUser submitted  
uo_is_sent has 2 status - "Submitted" "Sent"

*success response*
```
{
  "user_orders": [
    {
      "c_id": 1,
      "tu_id": 1,
      "uo_create_time": "2017-06-01 20:53:09",
      "uo_id": 65,
      "uo_is_buy": 1,
      "uo_is_sent": "Sent",
      "uo_limit_value": "",
      "uo_month": 5,
      "uo_price": -1,
      "uo_status": "Placed",
      "uo_stop_value": "",
      "uo_type": "Market",
      "uo_vol": 10,
      "uo_year": 2018
    },
    {
      "c_id": 1,
      "tu_id": 1,
      "uo_create_time": "2017-06-01 20:53:06",
      "uo_id": 64,
      "uo_is_buy": 1,
      "uo_is_sent": "Sent",
      "uo_limit_value": "",
      "uo_month": 5,
      "uo_price": -1,
      "uo_status": "Placed",
      "uo_stop_value": "",
      "uo_type": "Market",
      "uo_vol": 10,
      "uo_year": 2018
    }
  ]
}
```

/UserOrder/Cancelable GET
---
> Get all orders that is not fully completed

*success response*
```
{
  "cancelable_user_orders": [
    {
      "c_id": 1,
      "tu_id": 1,
      "uo_create_time": "2017-06-05 17:08:58",
      "uo_id": 174,
      "uo_is_buy": 1,
      "uo_is_sent": "Sent",
      "uo_limit_value": "40.3",
      "uo_max_order_vol": 90,
      "uo_month": 12,
      "uo_price": -1,
      "uo_status": "Placed",
      "uo_stop_value": "",
      "uo_time_limit": 1,
      "uo_type": "Market",
      "uo_vol": 320,
      "uo_year": 2018
    },
    {
      "c_id": 1,
      "tu_id": 1,
      "uo_create_time": "2017-06-05 16:20:33",
      "uo_id": 173,
      "uo_is_buy": 1,
      "uo_is_sent": "Sent",
      "uo_limit_value": "40.3",
      "uo_max_order_vol": 90,
      "uo_month": 12,
      "uo_price": -1,
      "uo_status": "Placed",
      "uo_stop_value": "",
      "uo_time_limit": 0,
      "uo_type": "Market",
      "uo_vol": 770,
      "uo_year": 2018
    }
  ]
}
```

/UserOrder/Cancel?uo_id=174 GET
---
> Cancel the userOrder which uo_id is 174

*success response*
```
{
  "msg": "Success"
}
```

/Commodity/All GET
---
> Get all commodities that a trader subscribed 

*success response*
```
{
  "commodities": [
    {
      "bu_id": 1,
      "c_id": 1,
      "c_name": "gold",
      "c_price": 225
    },
    {
      "bu_id": 1,
      "c_id": 2,
      "c_name": "corn",
      "c_price": 15.3
    }
  ]
}
``` 

/Commodity/{c_id}/BrokerUser GET
---
> Get the information of BrokerUser that sells a commodity  
> c_id is the id of a commodity

*success response*
```
{
  "bu_email": "hmcai@sjtu.edu.cn",
  "bu_id": 1,
  "bu_name": "caihongming",
  "bu_password": ""
}
```

/Order/All GET
---
> Get all orders (not trader order)

*success response*
```
{
  "orders": [
    {
      "c_id": 1,
      "o_create_time": "2017-05-30 00:03:13",
      "o_id": 20,
      "o_is_buy": 0,
      "o_limit_value": "",
      "o_month": 6,
      "o_price": -1,
      "o_status": "Placed",
      "o_stop_value": "234.99",
      "o_type": "Limit",
      "o_vol": 192,
      "o_year": 2017,
      "t_id": 1
    },
    {
      "c_id": 1,
      "o_create_time": "2017-05-30 00:03:13",
      "o_id": 21,
      "o_is_buy": 0,
      "o_limit_value": "-1",
      "o_month": 5,
      "o_price": -1,
      "o_status": "Part_Completed",
      "o_stop_value": "-1",
      "o_type": "Market",
      "o_vol": 175,
      "o_year": 2018,
      "t_id": 1
    },
    {
      "c_id": 1,
      "o_create_time": "2017-05-30 00:49:38",
      "o_id": 22,
      "o_is_buy": 1,
      "o_limit_value": "",
      "o_month": 5,
      "o_price": -1,
      "o_status": "Completed",
      "o_stop_value": "",
      "o_type": "Market",
      "o_vol": 10,
      "o_year": 2018,
      "t_id": 1
    }
  ]
}
``` 

/BrokerUser/All GET
---
> Get all brokerUsers in the market

*success response*
```
{
  "brokerUsers": [
    {
      "bu_email": "hmcai@sjtu.edu.cn",
      "bu_id": 1,
      "bu_name": "caihongming",
      "bu_password": "caihongming"
    }
  ]
}
```

/BrokerUser/{bu_id}/Commodities GET
---
> Get all commodities a brokerUser sells  
{bu_id} is the brokerUser id

*success response*
```
{
  "commodities": [
    {
      "bu_id": 1,
      "c_id": 1,
      "c_name": "gold",
      "c_price": 80
    },
    {
      "bu_id": 1,
      "c_id": 2,
      "c_name": "corn",
      "c_price": 15.3
    },
    {
      "bu_id": 1,
      "c_id": 3,
      "c_name": "crude",
      "c_price": 100
    }
  ]
}
```

/MarketDepth?cid=1&year=2018&month=5&isFloat=0 GET
---
> Get the current market depth

> Request parameter:  
cid -> commodity id  
year -> year  
month -> month in this year  
isFloat -> 1 if is floating leg, 0 otherwise

*success response*
```
{
  "c_id": 1,
  "o_year": 2018,
  "o_month": 5,
  "isFloat": 0,
  "buy": [],
  "sell": [
    {
      "c_id": 1,
      "former_o_id": 0,
      "isFloat": 0,
      "o_create_time": "2017-05-30 00:49:38",
      "o_id": 22,
      "o_is_buy": 0,
      "o_limit_value": 100,
      "o_month": 5,
      "o_price": -1,
      "o_status": "Part_Completed",
      "o_stop_value": -1,
      "o_type": "Limit",
      "o_vol": 2740,
      "o_year": 2018,
      "stop_or_limit_value": 100,
      "t_id": 1
    },
    {
      "c_id": 1,
      "former_o_id": 1,
      "isFloat": 0,
      "o_create_time": "2017-05-29 00:49:38",
      "o_id": 21,
      "o_is_buy": 0,
      "o_limit_value": 150,
      "o_month": 5,
      "o_price": -1,
      "o_status": "Placed",
      "o_stop_value": -1,
      "o_type": "Limit",
      "o_vol": 2000,
      "o_year": 2018,
      "stop_or_limit_value": 150,
      "t_id": 1
    }
  ]
}
```
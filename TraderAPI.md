## Traveller API

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

*request body*
```
{
  "c_id": 2,
  "uo_type": "Market",
  "uo_vol": 350,
  "uo_year": 2018,
  "uo_month": 10,
  "uo_limit_value": "",
  "uo_stop_value": ""
}
``` 
```
{
  "c_id": 2,
  "uo_type": "Limit",
  "uo_vol": 350,
  "uo_year": 2018,
  "uo_month": 10,
  "uo_limit_value": "3.7%",
  "uo_stop_value": ""
}
```
```
{
    "c_id": 2,
    "uo_type": "Stop",
    "uo_vol": 350,
    "uo_year": 2018,
    "uo_month": 10,
    "uo_limit_value": "",
    "uo_stop_value": "229.6"
}
```

*success response*
```
{
    "uo_id": 321,
    "o_id": -1,
    "c_id": 2,
    "tu_id": 1,
    "uo_price: -1,
    "uo_type": "Limit",
    "uo_vol": 350,
    "uo_status": "Placed",
    "uo_create_time": "2017-05-15 22:21:30",
    "uo_year": 2018,
    "uo_month": 10,
    "uo_limit_value": "3.7%",
    "uo_stop_value": ""
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

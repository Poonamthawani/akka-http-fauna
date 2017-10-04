# akka-http-fauna
Post to below url to add Friend
http://127.0.0.1:5009/addfriend
{
    "name":"BenEdwards",
    "city":"Croydon",
    "State":"Ca",
    "location":"UK"
}


Post to below url to checkinlocation

http://127.0.0.1:5009/checkinlocation
{
    "name":"BenEdwards",
    "city":"Croydon",
    "State":"Ca",
    "location":"UKupdate"
}

Get Friend
http://127.0.0.1:5009/findfriendbyname?name=BenEdwards
"VSuccess(Friend1(BenEdwards,Croydon,Ca,UK),/data/location)"

Get Friend by location
http://127.0.0.1:5009/findfriendbylocation/UK

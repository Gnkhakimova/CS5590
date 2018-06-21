/**
 * Created by Vijaya Yeruva on 5/27/2017.
 */

var http = require('http');
var MongoClient = require('mongodb').MongoClient;
var url = 'mongodb://gkhakimova:ayajonam777@ds263500.mlab.com:63500/cs5590';

MongoClient.connect(url, function(err, db) {
    if (err) throw err;
    var dbase = db.db("cs5590");
    var myquery = { address: 'Main Road 989' };
    dbase.collection("newCollection").deleteOne(myquery, function(err, obj) {
        if (err) throw err;
        console.log(obj.result.n + " document(s) deleted");
        db.close();
    });
});
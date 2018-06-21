/**
 * Created by Vijaya Yeruva on 5/27/2017.
 */
var MongoClient = require('mongodb').MongoClient;
var url = 'mongodb://gkhakimova:ayajonam777@ds263500.mlab.com:63500/cs5590';

MongoClient.connect(url, function(err, db) {
    if (err) throw err;
    var dbase = db.db("cs5590");
    dbase.createCollection("newCollection", function(err, res) {
        if (err) throw err;
        console.log("Collection created!");
        db.close();
    });
});

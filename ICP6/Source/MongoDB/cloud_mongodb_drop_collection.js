/**
 * Created by Vijaya Yeruva on 5/27/2017.
 */

var MongoClient = require('mongodb').MongoClient;
var url = 'mongodb://gkhakimova:ayajonam777@ds263500.mlab.com:63500/cs5590';

MongoClient.connect(url, function(err, db) {
    if (err) throw err;
    var dbase = db.db("cs5590");
    dbase.dropCollection("newCollection", function(err, delOK) {
        if (err) throw err;
        if (delOK) console.log("Collection deleted");
        db.close();
    });
});
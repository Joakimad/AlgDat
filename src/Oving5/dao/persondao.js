const Dao = require("./dao.js");

module.exports = class PersonDao extends Dao {
  getAll(callback) {
    super.query("select navn, alder, adresse from person", [], callback);
  }


  getOne(id, callback) {
    super.query(
      "select navn, alder, adresse from person where id=?",
      [id],
      callback
    );
  }

  createOne(json, callback) {
    var val = [json.navn, json.adresse, json.alder];
    super.query(
      "insert into person (navn,adresse,alder) values (?,?,?)",
      val,
      callback
    );
  }
//Lagt inn slett
  deleteOne(json, callback){
    super.query("delete from person where id=?", [json.id], callback);
  }
//Lagt inn Update
  updateOne(json, callback){
    var val = [json.navn, json.adresse, json.alder, json.id];
    super.query("update person set navn=?, alder=?, adresse=? where id=?", val, callback);
  }
};


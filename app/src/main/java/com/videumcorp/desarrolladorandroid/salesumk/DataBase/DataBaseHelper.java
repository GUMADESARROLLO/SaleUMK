package com.videumcorp.desarrolladorandroid.salesumk.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorJoiner;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by marangelo.php on 23/05/2016.
 */
public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "DataUMK.db";

    //TABLA USUARIOS
    public static final String TABLE_USUARIO = "USUARIOS";
    public static final String COL_1 = "US";
    public static final String COL_22 = "NOMBRE";
    public static final String COL_2 = "PS";


    //TABLAS ARTICULOS
    public static final String TABLE_ARTICULO = "Articulo";
    public static final String COL_3 = "ARTICULO";
    public static final String COL_4 = "DESCRIPCION";
    public static final String COL_5 = "CANTIDAD";
    public static final String COL_6 = "PRECIO";
    public static final String COL_34 = "PUNTOS";
    public static final String COL_35 = "BODEGAS";
    public static final String COL_36 = "REGLAS";


    //TABLA INVENTARIO A LIQUIDAR A 6 MESES
    public static final String TABLE_LIQ6 = "LIQ6";
    public static final String COL_7 = "ARTICULO";
    public static final String COL_8 = "DESCRIPCION";
    public static final String COL_9 = "DIAS_VENCIMIENTO";
    public static final String COL_10 = "CANT_DISPONIBLE";
    public static final String COL_11 = "FECHA_VENCE";
    public static final String COL_12 = "LOTE";

    //TABLA INVENTARIO A LIQUIDAR A 6 MESES
    public static final String TABLE_LIQ12 = "LIQ12";

    //TABLA INVENTARIO A LIQUIDAR A 6 MESES
    public static final String TABLE_CLIENTE = "CLIENTES";

    public static final String COL_13 = "CLIENTE";
    public static final String COL_14 = "NOMBRE";
    public static final String COL_15 = "DIRECCION";
    public static final String COL_16 = "TELEFONO1";
    public static final String COL_17 = "MOROSO";
    public static final String COL_18 = "LIMITE_CREDITO";
    public static final String COL_19 = "SALDO";
    public static final String COL_20 = "DISPO";

    public static final String COL_23 = "NoVencidos";
    public static final String COL_24 = "Dias30";
    public static final String COL_25 = "Dias60";
    public static final String COL_26 = "Dias90";
    public static final String COL_27 = "Dias120";
    public static final String COL_28 = "Mas120";

    public static final String COL_21 = "LSTUDTP";

    public static final String TABLE_EXISTENCIA_LOTE = "EXISTENCIA_LOTE";
    public static final String COL_33 = "ARTICULO";
    public static final String COL_29 = "LOTE";
    public static final String COL_30 = "FECHA_VENCIMIENTO";
    public static final String COL_31 = "CANT_DISPONIBLE";
    public static final String COL_32 = "BODEGA";

    public static final String TABLE_FACTURAS = "FACTURAS";
    public static final String COL_37 = "FACTURA";
    public static final String COL_38 = "CLIENTE";
    public static final String COL_39 = "VENDEDOR";
    public static final String COL_40 = "MONTO";
    public static final String COL_41 = "SALDO";



    public DataBaseHelper(Context context) {
        super(context.getApplicationContext(), DATABASE_NAME,null,1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_USUARIO + "("+ COL_1 +" TEXT,"+ COL_2 +" TEXT, "+ COL_21 +" TEXT,"+ COL_22 +" TEXT)");

        db.execSQL("create table " + TABLE_FACTURAS +" ("+
                COL_37 +" TEXT,"+
                COL_38 +" TEXT,"+
                COL_39 +" TEXT,"+
                COL_40 +" TEXT, "+
                COL_41 +" TEXT)");

        db.execSQL("create table " + TABLE_ARTICULO +" ("+
                COL_3 +" TEXT,"+
                COL_4 +" TEXT,"+
                COL_5 +" TEXT,"+
                COL_6 +" TEXT, "+
                COL_21 +" TEXT ,"+
                COL_34 +" TEXT,"+
                COL_35 +" TEXT,"+
                COL_36 +" TEXT)");
        db.execSQL("create table " + TABLE_LIQ6 +" ("+ COL_7 +" TEXT,"+ COL_8 +" TEXT,"+ COL_9 +" INTEGER,"+ COL_10 +" TEXT, "+ COL_11 +" TEXT, "+COL_12+" TEXT, "+ COL_21 +" TEXT )" );
        db.execSQL("create table " + TABLE_LIQ12 +" ("+ COL_7 +" TEXT,"+ COL_8 +" TEXT,"+ COL_9 +" INTEGER,"+ COL_10 +" TEXT, "+ COL_11 +" TEXT, "+COL_12+" TEXT, "+ COL_21 +" TEXT )" );
        db.execSQL("create table " + TABLE_CLIENTE +" ("+
                COL_13 +" TEXT,"+
                COL_14 +" TEXT,"+
                COL_15 +" TEXT,"+
                COL_16 +" TEXT,"+
                COL_17 +" TEXT,"+
                COL_18 +" TEXT,"+
                COL_19 +" TEXT,"+
                COL_20 +" TEXT,"+
                COL_21 +" TEXT,"+
                COL_23 +" TEXT,"+
                COL_24 +" TEXT,"+
                COL_25 +" TEXT,"+
                COL_26 +" TEXT,"+
                COL_27 +" TEXT,"+
                COL_28 +" TEXT )" );
        db.execSQL("create table " + TABLE_EXISTENCIA_LOTE + "("+ COL_29 +" TEXT, "+COL_33+" TEXT,"+ COL_30 +" TEXT, "+ COL_31 +" TEXT,"+ COL_32 +" TEXT)");
        
        db.execSQL("CREATE TABLE Recibo (" +
                "IdRecibo  INTEGER," +
                "IdCliente  TEXT," +
                "IdVendedor  TEXT," +
                "Fecha  TEXT," +
                "MRecibido  REAL," +
                "TC  REAL," +
                "TM  BLOB," +
                "Recibimos  TEXT," +
                "LCantidad  TEXT," +
                "Concepto  TEXT," +
                "Efectivo  BLOB," +
                "CHK  BLOB," +
                "NumCHK  TEXT," +
                "Banco  TEXT" +
                ")");
        
        db.execSQL("CREATE TABLE RDetalle (" +
                "IdRD  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "IDRecibo  INTEGER," +
                "NFactura  TEXT," +
                "FValor  REAL," +
                "ValorNC  REAL," +
                "Retencion  REAL," +
                "Descuento  REAL," +
                "VRecibo  REAL," +
                "Saldo  REAL" +
                ")");
        db.execSQL("CREATE TABLE observaciones (" +
                "Descrip  TEXT," +
                "IdObserv  INTEGER" +
                ");");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS observaciones ");
        db.execSQL("DROP TABLE IF EXISTS RDetalle ");
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS Recibo");
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_USUARIO);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_ARTICULO);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_LIQ6);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_LIQ12);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_CLIENTE);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_EXISTENCIA_LOTE);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_FACTURAS);
        onCreate(db);
    }

    public boolean insertFacturas(String FACTURA, String CLIENTE,String VENDEDOR,String MONTO, String SALDO){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_37,FACTURA);
        contentValues.put(COL_38,CLIENTE);
        contentValues.put(COL_39,VENDEDOR);
        contentValues.put(COL_40,MONTO);
        contentValues.put(COL_41,SALDO);
        long result = db.insert(TABLE_FACTURAS,null,contentValues);
        if (result == -1){
            return false;
        }else{
            return true;
        }
    }
    public boolean insertDataUS(String US, String PSS,String Nombre){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,US);
        contentValues.put(COL_2,PSS);
        contentValues.put(COL_22,Nombre);
        contentValues.put(COL_21,Datetime());
        long result = db.insert(TABLE_USUARIO,null,contentValues);
        if (result == -1){
            return false;
        }else{
            return true;
        }
    }
    public boolean insertExiLote(String ARTICULO,String LOTE, String FECHA_VENCIMIENTO,String CANT_DISPONIBLE,String BODEGA){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_33,ARTICULO);
        contentValues.put(COL_29,LOTE);
        contentValues.put(COL_30,FECHA_VENCIMIENTO);
        contentValues.put(COL_31,CANT_DISPONIBLE);
        contentValues.put(COL_32,BODEGA);
        long result = db.insert(TABLE_EXISTENCIA_LOTE,null,contentValues);
        if (result == -1){
            return false;
        }else{
            return true;
        }
    }
    public boolean insertDataArticulo(String Articulo, String Descrip,String Exist,String Precio,String PUNTOS,String BODEGAS,String REGLAS){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_3,Articulo);
        contentValues.put(COL_4,Descrip);
        contentValues.put(COL_5,Exist);
        contentValues.put(COL_6,Precio);
        contentValues.put(COL_34,PUNTOS);
        contentValues.put(COL_35,BODEGAS);
        contentValues.put(COL_36,REGLAS);
        contentValues.put(COL_21,Datetime());
        long result = db.insert(TABLE_ARTICULO,null,contentValues);
        if (result == -1){
            return false;
        }else{
            return true;
        }
    }
    public boolean insertDataLIQ6(String Articulo, String Descrip,String DiasVencerce,String Dispo,String FechaVencimiento,String Lote){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_7,Articulo);
        contentValues.put(COL_8,Descrip);
        contentValues.put(COL_9,DiasVencerce);
        contentValues.put(COL_10,Dispo);
        contentValues.put(COL_11,FechaVencimiento);
        contentValues.put(COL_12,Lote);
        contentValues.put(COL_21,Datetime());
        long result = db.insert(TABLE_LIQ6,null,contentValues);
        if (result == -1){
            return false;
        }else{
            return true;
        }
    }
    public boolean insertDataLIQ12(String Articulo, String Descrip,String DiasVencerce,String Dispo,String FechaVencimiento,String Lote){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_7,Articulo);
        contentValues.put(COL_8,Descrip);
        contentValues.put(COL_9,DiasVencerce);
        contentValues.put(COL_10,Dispo);
        contentValues.put(COL_11,FechaVencimiento);
        contentValues.put(COL_12,Lote);
        contentValues.put(COL_21,Datetime());
        long result = db.insert(TABLE_LIQ12,null,contentValues);
        if (result == -1){
            return false;
        }else{
            return true;
        }
    }
    public boolean insertDataCliente(String CLIENTE, String NOMBRE,String DIRECCION,String TELEFONO1,String MOROSO,String LIMITE_CREDITO,String SALDO,String DISPO,String NoVencidos,String Dias30,String Dias60,String Dias90,String Dias120,String Mas120){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_13,CLIENTE);
        contentValues.put(COL_14,NOMBRE);
        contentValues.put(COL_15,DIRECCION);
        contentValues.put(COL_16,TELEFONO1);
        contentValues.put(COL_17,MOROSO);
        contentValues.put(COL_18,LIMITE_CREDITO);
        contentValues.put(COL_19,SALDO);
        contentValues.put(COL_20,DISPO);
        contentValues.put(COL_21,Datetime());
        contentValues.put(COL_23,NoVencidos);
        contentValues.put(COL_24,Dias30);
        contentValues.put(COL_25,Dias60);
        contentValues.put(COL_26,Dias90);
        contentValues.put(COL_27,Dias120);
        contentValues.put(COL_28,Mas120);

        long result = db.insert(TABLE_CLIENTE,null,contentValues);
        if (result == -1){
            return false;
        }else{
            return true;
        }
    }public String Datetime(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String Date = (dateFormat.format(cal.getTime()));
        return Date;
    }
    public Cursor GetAllData(String U,String P){
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "SELECT * FROM " + TABLE_USUARIO +" WHERE "+ COL_1 +"="+ '"' + U.trim().toUpperCase() + '"' +" and "+ COL_2 +"="+ '"' +P.trim().toUpperCase() + '"' +"";
        Cursor res = db.rawQuery(Query ,null);
        return res;
    }

    public Cursor GetData(String Table){
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "SELECT * FROM " + Table;
        Cursor res = db.rawQuery(Query ,null);
        return res;
    }
    public String GetNameUser(String Usr){
        SQLiteDatabase db = this.getWritableDatabase();
        String Nombre="";
        String Query = "SELECT * FROM " + TABLE_USUARIO + " WHERE "+ COL_1 +"="+ '"'+ Usr.trim().toUpperCase()+'"';
        Cursor res = db.rawQuery(Query ,null);
        if (res.getCount()==0){
            Nombre ="Error Acreditación";
        }else{
            if (res.moveToFirst()) {
                do {
                    Nombre = res.getString(3);
                } while(res.moveToNext());
            }

        }
        return Nombre;
    }
    public Cursor GetInfoArt(String Id){
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "SELECT * FROM " + TABLE_ARTICULO + " WHERE "+ COL_3 +"="+ '"'+ Id.trim()+'"';
        Cursor res = db.rawQuery(Query ,null);
        return res;
    }
    public Cursor GetLotesArt(String Id){
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "SELECT * FROM " + TABLE_EXISTENCIA_LOTE + " WHERE "+ COL_33 +"="+ '"'+ Id.trim()+'"';
        Cursor res = db.rawQuery(Query ,null);
        return res;
    }
    public Cursor GetLiq(String Id){
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "SELECT * FROM " + TABLE_LIQ12 + " WHERE "+ COL_7 +"="+ '"'+ Id.trim()+'"';
        Cursor res = db.rawQuery(Query ,null);
        return res;
    }
    public Cursor InfoCliente(String Id){
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "SELECT * FROM " + TABLE_CLIENTE + " WHERE "+ COL_13 +"="+ '"'+ Id.trim()+'"';
        Cursor res = db.rawQuery(Query ,null);
        return res;
    }
    public Cursor InfoClienteFactura(String Id){
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "SELECT * FROM " + TABLE_FACTURAS + " WHERE "+ COL_38 +"="+ '"'+ Id.trim()+'"';
        Cursor res = db.rawQuery(Query ,null);
        return res;
    }

}

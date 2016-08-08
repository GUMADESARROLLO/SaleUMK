package com.guma.desarrollo.salesumk.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.renderscript.Sampler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Switch;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by marangelo.php on 23/05/2016.
 */
public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "DataUMK.db";

    //TABLA USUARIOS
    public static final String TABLE_USUARIO = "Usuarios";
    public static final String COL_1 = "IdVendedor";
    public static final String COL_22 = "NombreUsuario";
    public static final String COL_2 = "Password";


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
        super(context.getApplicationContext(), DATABASE_NAME,null,2);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL("create table " + TABLE_USUARIO + "("+ COL_1 +" TEXT,"+ COL_2 +" TEXT, "+ COL_21 +" TEXT,"+ COL_22 +" TEXT)");

        db.execSQL("create table " + TABLE_FACTURAS +" ("+
                COL_37 +" TEXT,"+
                COL_38 +" TEXT,"+
                COL_39 +" TEXT,"+
                COL_40 +" TEXT, "+
                COL_41 +" TEXT, "+
                "FECHA_VENCE TEXT)");

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


        db.execSQL("create table  Actividad" +
                " (" +
                "       IdAE              INTEGER," +
                "       Actividad         TEXT(150)" +
                ")");

        db.execSQL("create table  AE" +
                " (" +
                "       IdPlan            TEXT(10)," +
                "       IdCliente         TEXT(50)," +
                "       IdAE              INTEGER," +
                "       IdEje             INTEGER," +
                "       Contacto1         INTEGER(1)," +
                "       Contacto2         INTEGER(1)," +
                "       Observacion       TEXT(250)," +
                "       Fecha             DATETIME" +
                ")");

        db.execSQL("create table  Agenda" +
                " (" +
                "       IdPlan            TEXT(10) primary key not null," +
                "       Vendedor          TEXT(150)," +
                "       Ruta              TEXT(10)," +
                "       Zona              TEXT(50)," +
                "       Revisado          TEXT(100)" +
                ")");

        db.execSQL("create table  PDetalle" +
                " (" +
                "       IdDP              INTEGER primary key not null," +
                "       IdPedido          INTEGER," +
                "       IdArticulo        TEXT(50)," +
                "       Descripcion       TEXT(250)," +
                "       Cantidad          NUMERIC," +
                "       Precio            NUMERIC" +
                ")");

        db.execSQL("create table  Ejecutiva" +
                " (" +
                "       IdEje             INTEGER primary key not null," +
                "       IdAE              INTEGER," +
                "       Actividad         TEXT(150)" +
                ")");

        db.execSQL("create table  Pedido" +
                " (" +
                "       IdPedido          INTEGER primary key not null," +
                "       IdVendedor        TEXT(10)," +
                "       IdCliente         TEXT(50)," +
                "       Fecha             DATETIME," +
                "       Vendedor          TEXT(150)," +
                "       Cliente           TEXT(150)," +
                "       Direccion         TEXT(250)," +
                "       TM                INTEGER(1)," +
                "       FP                INTEGER(1)," +
                "       Plazo             TEXT(50)," +
                "       Descuento         NUMERIC," +
                "       IVA               NUMERIC," +
                "       Nota              TEXT(250)" +
                ")");

        db.execSQL("create table  RDetalle" +
                " (" +
                "       IdRecibo          INTEGER," +
                "       NFactura          TEXT(50)," +
                "       FValor            NUMERIC," +
                "       ValorNC           NUMERIC," +
                "       Retencion         NUMERIC," +
                "       Descuento         NUMERIC," +
                "       VRecibo           NUMERIC," +
                "       Saldo             NUMERIC" +
                ")");

        db.execSQL("create table  Recibo" +
                " (" +
                "       IdRecibo          TEXT(14) primary key not null," +
                "       IdCliente         TEXT(50)," +
                "       Vendedor          TEXT(150)," +
                "       Fecha             DATETIME," +
                "       MRecibido         NUMERIC," +
                "       TC                NUMERIC," +
                "       TM                INTEGER(1)," +
                "       Recibimos         TEXT(250)," +
                "       LCantidad         TEXT(250)," +
                "       Concepto          TEXT(250)," +
                "       Efectivo          INTEGER(1)," +
                "       CHK               INTEGER(1)," +
                "       NumCHK            TEXT(50)," +
                "       Banco             TEXT(100)" +
                ")");




        db.execSQL("create table  Usuarios" +
                " (" +
                "       UsuarioID         INTEGER primary key not null," +
                "       IdVendedor        TEXT(10)," +
                "       NombreUsuario     TEXT(100)," +
                "       Password          TEXT(20)," +
                "       Privilegio        INTEGER," +
                "       Activo            INTEGER(1)," +
                "       FechaCreacion     DATETIME" +
                ")");

        db.execSQL("create table  VClientes" +
                " (" +
                "       IdPlan            TEXT(10)," +
                "       Lunes             TEXT(250)," +
                "       Martes            TEXT(250)," +
                "       Miercoles         TEXT(250)," +
                "       Jueves            TEXT(250)," +
                "       Viernes           TEXT(250)," +
                "       Sabado            TEXT(250)," +
                "       Observaciones     TEXT(250)" +
                ")");

        db.execSQL("create table  Visitas" +
                " (" +
                "       IdPlan            TEXT(10) ," +
                "       IdCliente         TEXT(50)," +
                "       Fecha             DATETIME," +
                "       Visitado          INTEGER(1)" +
                ")");
        
        db.execSQL("create table  GVendedor" +
                " (" +
                "       UsuarioID         INTEGER," +
                "       IdVendedor        TEXT(10)," +
                "       Activa            INTEGER(1)," +
                "       FechaB            DATETIME" +
                ");");


        db.execSQL("CREATE TABLE observaciones (" +
                "Descrip  TEXT," +
                "IdObserv  INTEGER" +
                ");");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Visitas ");
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS VClientes ");
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS Pedido ");
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS DPedido ");
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS Agenda ");
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS AE ");
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS Actividad ");
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS observaciones ");
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS RDetalle ");
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS Recibo");
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS Usuarios");
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

    public boolean insertFacturas(String FACTURA, String CLIENTE,String VENDEDOR,String MONTO, String SALDO,String FECHA_VENCE){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_37,FACTURA);
        contentValues.put(COL_38,CLIENTE);
        contentValues.put(COL_39,VENDEDOR);
        contentValues.put(COL_40,MONTO);
        contentValues.put(COL_41,SALDO);
        contentValues.put("FECHA_VENCE",FECHA_VENCE);
        long result = db.insert(TABLE_FACTURAS,null,contentValues);
        if (result == -1){
            return false;
        }else{
            return true;
        }
    }
    public boolean insertDataUS(String Key,String US, String PSS,String Nombre){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("UsuarioID",Key);
        contentValues.put(COL_1,US);
        contentValues.put(COL_2,PSS);
        contentValues.put(COL_22,Nombre);
        contentValues.put("FechaCreacion",Datetime());
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
    }
    public boolean SaveRecibo(
            String varIdRecibo,
            String varIdCliente,
            String varCodVendedor,
            String varIdVendedor,
            String varFecha,
            String varMRecibido,
            String varTC,
            String varTM,
            String varRecibimos,
            String varLCantidad,
            String varConcepto,
            boolean varEfectivo,boolean varCHK,
            String varNumCHK,
            String varBanco
            ){

        String CodR = varCodVendedor + "-"+ varIdRecibo;


       SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("IdRecibo",CodR);
        contentValues.put("IdCliente",varIdCliente);
        contentValues.put("Vendedor",varIdVendedor);
        contentValues.put("Fecha",varFecha);
        contentValues.put("MRecibido",varMRecibido);
        contentValues.put("TC",varTC);
        contentValues.put("TM",varTM);
        contentValues.put("Recibimos",varRecibimos);
        contentValues.put("LCantidad",varLCantidad);
        contentValues.put("Concepto",varConcepto);
        contentValues.put("Efectivo",varEfectivo);
        contentValues.put("CHK",varCHK);
        contentValues.put("NumCHK",varNumCHK);
        contentValues.put("Banco",varBanco);

        long result = db.insert("Recibo",null,contentValues);
        if (result == -1){
            return false;
        }else{
            return true;
        }

    }
    public void SaveDetalleRecibo(String SQL){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d("SQLPARAINCERT","INSERT INTO rdetalle VALUES " + SQL);
        db.execSQL("INSERT INTO rdetalle VALUES " + SQL);
        db.close();
    }
    public String Datetime(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String Date = (dateFormat.format(cal.getTime()));
        return Date;
    }
    public Cursor GetAllData(String U,String P){
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "SELECT * FROM " + TABLE_USUARIO +" WHERE "+ COL_1 +"="+ '"' + U.trim().toUpperCase() + '"' +" and "+ COL_2 +"="+ '"' +P.trim().toUpperCase() + '"' +"";
        Log.d("Query",Query);
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
                    Nombre = res.getString(2);
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
    public String[] GetCobros(String Id){
        SQLiteDatabase db = this.getWritableDatabase();
        int i=0;
        String Query = "SELECT * FROM Recibo WHERE IdCliente="+ '"'+ Id.trim()+'"';
        Cursor res = db.rawQuery(Query ,null);
        String vareturn[] = new String[res.getCount()];
        if (res.getCount()!=0){
            if (res.moveToFirst()) {
                do {
                    vareturn[i] = res.getString(0)+","+res.getString(3)+","+res.getString(4);
                    i++;
                } while(res.moveToNext());
            }
        }
        return vareturn;
    }

    public Cursor GetInfoRecibo(String Id){
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "SELECT * FROM Recibo WHERE IdRecibo="+ '"'+ Id.trim()+'"';
        Cursor res = db.rawQuery(Query ,null);
        return res;
    }
    public Cursor GetInfoRDetalle(String Id){
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "SELECT * FROM RDetalle WHERE IdRecibo="+ '"'+ Id.trim()+'"';
        Cursor res = db.rawQuery(Query ,null);
        return res;
    }
    public Cursor GetPlanTrabajo(String Id){
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "SELECT * FROM Agenda WHERE Ruta="+ '"'+ Id.trim()+'"';
        Cursor res = db.rawQuery(Query ,null);
        return res;
    }
    public boolean DeleteClienteDia(String Id,int Dia,String Cliente){
        String[] Semana = {"Lunes","Martes","Miercoles","Jueves","Viernes"};
        String NewCadena="";
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String Query = "SELECT "+ Semana[Dia] +" FROM VClientes WHERE IdPlan="+ '"'+ Id.trim()+'"';
        Cursor res = db.rawQuery(Query ,null);

        if (res.getCount()==0){
        }else{
            if (res.moveToFirst()) {
                do {
                    String[] Arrays = res.getString(0).replace(Cliente,"").split(",");
                    for (int r=0;r < Arrays.length;r++){
                        if (!TextUtils.isEmpty(Arrays[r])){
                            NewCadena +=Arrays[r]+",";
                        }
                    }
                } while(res.moveToNext());
            }
        }
        if (NewCadena.length()!=0){
            NewCadena = NewCadena.substring(0,NewCadena.length()-1);
        }

        values.put(Semana[Dia], NewCadena);
        long result  = db.update("VClientes", values, "IdPlan" + "= '" + Id + "'", null);
        db.close();

        if (result == -1){
            return false;
        }else{
            return true;
        }
    }
    public String[] GetDiaPlanTrabajo(String Id,String Dia){
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "SELECT ifnull("+ Dia +",0) As "+ Dia +"  FROM VClientes WHERE IdPlan="+ '"'+ Id.trim()+'"';

        Cursor res = db.rawQuery(Query ,null);
        int i=0;
        String Clientes[] = new String[res.getCount()];
        if (res.getCount()!=0){
            if (res.moveToFirst()) {
                do {
                    Clientes[i] = "WHERE CLIENTE IN ('" + res.getString(0).replace(",","','") + "')";
                    i++;
                } while(res.moveToNext());
            }
        }
        String QueryWhoCliente ="SELECT CLIENTE,NOMBRE FROM CLIENTES " + Clientes[0];
        Cursor resWhoCliente = db.rawQuery(QueryWhoCliente,null);
        String Resultado[] = new String[resWhoCliente.getCount()];
        int c=0;
        if (resWhoCliente.getCount()!=0){
            if (resWhoCliente.moveToFirst()) {
                do {
                    Resultado[c] = resWhoCliente.getString(0)+ "_"+resWhoCliente.getString(1) ;
                    c++;
                } while(resWhoCliente.moveToNext());
            }
        }


        return Resultado;
    }
    public Cursor PushAgenda(String Id){
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "SELECT * FROM Agenda WHERE IdPlan="+ '"'+ Id.trim()+'"';
        Cursor res = db.rawQuery(Query ,null);
        return res;
    }
    public Cursor PushVCliente(String Id){
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "SELECT * FROM VClientes WHERE IdPlan="+ '"'+ Id.trim()+'"';
        Cursor res = db.rawQuery(Query ,null);
        return res;
    }
    public int GetCountPlanTrabajo(String Id){
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "SELECT * FROM Agenda WHERE IdPlan="+ '"'+ Id.trim()+'"';
        Cursor res = db.rawQuery(Query ,null);
        return res.getCount();
    }
    public boolean UpdateVCliente(String Id,String Dia,String Cliente){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String Query = "SELECT * FROM VClientes WHERE IdPlan="+ '"'+ Id.trim()+'"';
        Cursor res = db.rawQuery(Query ,null);
        if (res.getCount()==0){
        }else{
            if (res.moveToFirst()) {
                do {
                    switch(Dia) {
                        case "LUNES":
                            if (TextUtils.isEmpty(res.getString(1))){
                                values.put("Lunes", Cliente);
                            }else{
                                values.put("Lunes", res.getString(1)+","+Cliente);
                            }
                            break;
                        case "MARTES":
                            if (TextUtils.isEmpty(res.getString(2))){
                                values.put("Martes", Cliente);
                            }else{
                                values.put("Martes", res.getString(2)+","+Cliente);
                            }
                            break;
                        case "MIERCOLES":
                            if (TextUtils.isEmpty(res.getString(3))){
                                values.put("Miercoles", Cliente);
                            }else{
                                values.put("Miercoles", res.getString(3)+","+Cliente);
                            }
                            break;
                        case "JUEVES":
                            if (TextUtils.isEmpty(res.getString(4))){
                                values.put("Jueves", Cliente);
                            }else{
                                values.put("Jueves", res.getString(4)+","+Cliente);
                            }
                            break;
                        case "VIERNES":
                            if (TextUtils.isEmpty(res.getString(5))){
                                values.put("Viernes", Cliente);
                            }else{
                                values.put("Viernes", res.getString(5)+","+Cliente);
                            }
                            break;
                    }
                } while(res.moveToNext());
            }
        }
        long result  = db.update("VClientes", values, "IdPlan" + "= '" + Id + "'", null);
        db.close();
        if (result == -1){
            return false;
        }else{
            return true;
        }
    }
    public boolean insertPlanTRabajo(String ID,String Vendedor,String Ruta,String Zona,String Revisado){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues Agenda= new ContentValues();
        ContentValues VClientes = new ContentValues();

        Agenda.put("IdPlan",ID);
        Agenda.put("Vendedor",Vendedor);
        Agenda.put("Ruta",Ruta);
        Agenda.put("Zona",Zona);
        Agenda.put("Revisado",Revisado);
        long result = db.insert("Agenda",null,Agenda);

        VClientes.put("IdPlan",ID);
        VClientes.put("Lunes","");
        VClientes.put("Martes","");
        VClientes.put("Miercoles","");
        VClientes.put("Jueves","");
        VClientes.put("Viernes","");
        VClientes.put("Sabado","");
        VClientes.put("Observaciones","");
        db.insert("VClientes",null,VClientes);


        if (result == -1){
            return false;
        }else{
            return true;
        }
    }
    public Cursor GetAllRecibo(){
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "SELECT * FROM Recibo";
        Cursor res = db.rawQuery(Query ,null);
        return res;
    }
    public Cursor GetAllRDetalle(){
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "SELECT * FROM RDetalle";
        Cursor res = db.rawQuery(Query ,null);
        return res;
    }
    public boolean DeleteRecibo(String value){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete("Recibo", "IdRecibo" + "= '" + value + "'", null);
        long result  = db.delete("RDetalle", "IdRecibo" + "= '" + value + "'", null);
        db.close();
        if (result == -1){
            return false;
        }else{
            return true;
        }
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
    public String GetValorFacCobro(String Id){
        SQLiteDatabase db = this.getWritableDatabase();
        String Monto="";
        String Query = "SELECT * FROM " + TABLE_FACTURAS + " WHERE "+ COL_37 +"="+ '"'+ Id.trim()+'"';
        Cursor res = db.rawQuery(Query ,null);
        if (res.getCount()==0){
            Monto ="Error Acreditación";
        }else{
            if (res.moveToFirst()) {
                do {
                    Monto = res.getString(4);
                } while(res.moveToNext());
            }

        }
        return Monto;
    }

}

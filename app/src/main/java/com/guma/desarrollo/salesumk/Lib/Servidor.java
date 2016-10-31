package com.guma.desarrollo.salesumk.Lib;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

import com.guma.desarrollo.salesumk.DataBase.DataBaseHelper;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class Servidor
{
    DataBaseHelper myDB;
    Funciones vrf;
    ClssURL Url;
    ProgressDialog pdialog;

    public String[] PushPedidos(Context C)
    {
        String[] Dtos = new String[3];
        String SqlSyncInsert = "",SqlSyncInsertDetalles="",mPedidos="";
        myDB = new DataBaseHelper(C);
        Cursor res = myDB.dataUpdaload("Pedido",0);
        if (res.getCount()!=0)
        {
            if (res.moveToFirst())
            {
                do
                {
                    mPedidos += "'" + res.getString(0)+ "',";
                    SqlSyncInsert +=
                            "CALL SP_pedidos ("+
                                    "'"+res.getString(0)+"',"+
                                    "'"+res.getString(1)+"',"+
                                    "'"+res.getString(2)+"',"+
                                    "'"+res.getString(3)+"',"+
                                    "'"+res.getString(4)+"',"+
                                    "'"+res.getString(5)+"',"+
                                    "'"+res.getString(6)+"',"+
                                    "'"+res.getString(7)+"',"+
                                    "'"+res.getString(8)+"',"+
                                    "'"+res.getString(9)+"',"+
                                    "'"+res.getString(10)+"',"+
                                    "'"+res.getString(11)+"',"+
                                    "'"+res.getString(12)+"',"+
                                    "'0'"
                                    +");";
                } while(res.moveToNext());
                SqlSyncInsert = SqlSyncInsert.substring(0,SqlSyncInsert.length()-1);
                mPedidos = mPedidos.substring(0,mPedidos.length()-1);
            }
        }
        //DETALLES DE LOS PEDIDOS
        Cursor ResDetalle = myDB.getDetalle("PDETALLE",mPedidos,"IdPedido");
        if (ResDetalle.getCount()!=0)
        {
            SqlSyncInsertDetalles = "INSERT INTO PDetalle (IdPedido, IdArticulo, Descripcion, Cantidad, Precio, Bono) VALUES";
            if (ResDetalle.moveToFirst())
            {
                do
                {
                    SqlSyncInsertDetalles +=
                            " ("+
                                    "'"+ResDetalle.getString(0)+"',"+
                                    "'"+ResDetalle.getString(1)+"',"+
                                    "'"+ResDetalle.getString(2)+"',"+
                                    "'"+ResDetalle.getInt(3)+"',"+
                                    "'"+ResDetalle.getFloat(4)+"',"+
                                    "'"+ResDetalle.getString(5)+"'"
                                    +"),";
                } while(ResDetalle.moveToNext());
                SqlSyncInsertDetalles = SqlSyncInsertDetalles.substring(0,SqlSyncInsertDetalles.length()-1);
            }
        }

        Dtos[0]=SqlSyncInsert;
        Dtos[1]=SqlSyncInsertDetalles;
        Dtos[2]=mPedidos;
        return Dtos;
    }
    public String[] PushCobros(Context C)
    {
        String SqlSyncInsert = "",SqlSyncInsertDetalles="",mPedidos="";
        String[] Dtos = new String[3];
        myDB = new DataBaseHelper(C);
        Cursor res = myDB.dataUpdaload("Recibo",0);
        if (res.getCount()!=0)
        {
            SqlSyncInsert = "INSERT INTO recibo (IdRecibo, IdCliente, Vendedor, Fecha, MRecibo, TC, TM, Recibimos, LCantidad, Concepto, Efectivo, CHK, NumCHK, Banco) VALUES";
            if (res.moveToFirst())
            {
                do
                {
                    mPedidos += "'" + res.getString(0)+ "',";
                    SqlSyncInsert +=
                            "("+
                                    "'"+res.getString(0)+"',"+
                                    "'"+res.getString(1)+"',"+
                                    "'"+res.getString(2)+"',"+
                                    "'"+vrf.DateFormat(res.getString(3))+"',"+
                                    "'"+res.getString(4)+"',"+
                                    "'"+res.getString(5)+"',"+
                                    "'"+res.getString(6)+"',"+
                                    "'"+res.getString(7)+"',"+
                                    "'"+res.getString(8)+   "',"+
                                    "'"+res.getString(9)+"',"+
                                    "'"+res.getString(10)+"',"+
                                    "'"+res.getString(11)+"',"+
                                    "'"+res.getString(12)+"',"+
                                    "'"+res.getString(13)+"'"
                                    +"),";
                } while(res.moveToNext());
                SqlSyncInsert = SqlSyncInsert.substring(0,SqlSyncInsert.length()-1);
                mPedidos = mPedidos.substring(0,mPedidos.length()-1);
            }
        }
        //DETALLES DE LOS RECIBOS
        Cursor ResDetalle = myDB.getDetalle("RDetalle",mPedidos,"IdRecibo");
        if (ResDetalle.getCount()!=0)
        {
            SqlSyncInsertDetalles = "INSERT INTO rdetalle (IdRecibo, NFactura, FValor, ValorNC, Retencion, Descuento, VRecibo, Saldo) VALUES";
            if (ResDetalle.moveToFirst())
            {
                do
                {
                    SqlSyncInsertDetalles +=
                            "("+
                                    "'"+ResDetalle.getString(0)+"',"+
                                    "'"+ResDetalle.getString(1)+"',"+
                                    "'"+ResDetalle.getString(2)+"',"+
                                    "'"+ResDetalle.getString(3)+"',"+
                                    "'"+ResDetalle.getString(4)+"',"+
                                    "'"+ResDetalle.getString(5)+"',"+
                                    "'"+ResDetalle.getString(6)+"',"+
                                    "'"+ResDetalle.getString(7)+"'"
                                    +"),";
                } while(ResDetalle.moveToNext());
                SqlSyncInsertDetalles = SqlSyncInsertDetalles.substring(0,SqlSyncInsertDetalles.length()-1);
            }
        }
        Dtos[0]=SqlSyncInsert;
        Dtos[1]=SqlSyncInsertDetalles;
        Dtos[2]=mPedidos;
        return Dtos;
    }
    public void PushAll(final Context C)
    {
        AsyncHttpClient Cnx = new AsyncHttpClient();
        RequestParams paramentros = new RequestParams();

        String[] dtsCobro = PushCobros(C);
        String[] dtsPedidos = PushPedidos(C);

        /*
            Toast.makeText(C, dtsCobro[0], Toast.LENGTH_SHORT).show();
            Toast.makeText(C, dtsCobro[1], Toast.LENGTH_SHORT).show();
            Toast.makeText(C, dtsCobro[2], Toast.LENGTH_SHORT).show();
            Toast.makeText(C, dtsPedidos[0], Toast.LENGTH_SHORT).show();
            Toast.makeText(C, dtsPedidos[1], Toast.LENGTH_SHORT).show();
            Toast.makeText(C, dtsPedidos[2], Toast.LENGTH_SHORT).show();
        */

        paramentros.put("Pedidos",dtsPedidos[0]);
        paramentros.put("DPedidos",dtsPedidos[1]);
        paramentros.put("Recibos",dtsCobro[0]);
        paramentros.put("DRecibos",dtsCobro[1]);
        pdialog = ProgressDialog.show(C, "","Procesando, ya casi terminamos...", true);
        Cnx.post
        (Url.getURL_Asyn(), paramentros, new AsyncHttpResponseHandler()
            {
                @Override
                public void onSuccess(int statusCode, org.apache.http.Header[] headers, byte[] responseBody) {
                    if (statusCode==200){
                        Toast.makeText(C, "Correcto", Toast.LENGTH_SHORT).show();
                        //myDB.Update(finalLogReg);
                        pdialog.dismiss();
                    }else{
                        pdialog.dismiss();
                        Toast.makeText(C, "No hay respuesta del servidor", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(int statusCode, org.apache.http.Header[] headers, byte[] responseBody, Throwable error) {
                    pdialog.dismiss();
                    Toast.makeText(C, "Problemas de Cobertura de datos", Toast.LENGTH_SHORT).show();
                }
            }
        );
    }
}

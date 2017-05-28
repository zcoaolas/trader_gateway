package com.sjtu.zc.trader.util;

/**
 * Created by zcoaolas on 2017/5/28.
 *
 * 将json串中的日期字符串转换为bean中的Timestamp
 */
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import net.sf.ezmorph.MorphException;
import net.sf.ezmorph.object.AbstractObjectMorpher;

public class TimestampMorpher extends AbstractObjectMorpher {
    private String[] formats;
    public TimestampMorpher(String[] formats) {
        this.formats = formats;
    }
    public Object morph(Object value) {
        if( value == null){
            return null;
        }
        if( Timestamp.class.isAssignableFrom(value.getClass()) ){
            return (Timestamp) value;
        }
        if( !supports( value.getClass()) ){
            throw new MorphException( value.getClass() + " type is not Supported");
        }
        String strValue=(String) value;
        SimpleDateFormat dateParser=null;
        for( int i = 0; i < formats.length ; i++ ){
            if( null == dateParser ){
                dateParser=new SimpleDateFormat(formats[i]);
            }else{
                dateParser.applyPattern(formats[i]);
            }
            try{
                return new Timestamp( dateParser.parse( strValue.toLowerCase()).getTime() );}
            catch (ParseException e) {
                //e.printStackTrace();
            }
        }
        return null;
    }
    @Override
    public Class morphsTo() {
        return Timestamp.class;
    }
    public boolean supports( Class clazz ){
        return String.class.isAssignableFrom( clazz );
    }

}

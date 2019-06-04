package com.krxinuo.config;

import com.krxinuo.entity.SysLogEntity;
import com.krxinuo.util.SpringContextUtils;
import net.sf.json.JSONObject;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class HibernateInterceptor extends EmptyInterceptor {

    private SysLogEntity sysLogEntity;

    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        return super.onSave(entity, id, state, propertyNames, types);
    }

    @Override
    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {
        sysLogEntity = (SysLogEntity) SpringContextUtils.getBean("sysLog");
//        System.out.println("实例----------:"+ JSONObject.fromObject(entity).toString());
//        System.out.println("修改后----------:"+ JSONArray.fromObject(currentState).toString());
//        System.out.println("修改前----------:"+ JSONArray.fromObject(previousState).toString());
//        System.out.println("属性名----------:"+JSONArray.fromObject(propertyNames).toString());
        Map before = new HashMap();
        Map after = new HashMap();

        for (int i = 0; i < propertyNames.length; i++) {

            before.put(propertyNames[i],previousState[i]);
            after.put(propertyNames[i],currentState[i]);

        }
        sysLogEntity.setOptBefore(JSONObject.fromObject(before).toString());
        sysLogEntity.setOptAfter(JSONObject.fromObject(after).toString());
        return super.onFlushDirty(entity, id, currentState, previousState, propertyNames, types);
    }
}

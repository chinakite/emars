package com.ideamoment.emars.utils.mapper;

import com.ideamoment.emars.model.HistoriableEntity;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.Configuration;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yukiwang on 2018/2/22.
 */

public class SimpleUpdateExtendedLanguageDriver extends XMLLanguageDriver implements LanguageDriver {

    private final Pattern inPattern = Pattern.compile("\\(#\\{(\\w+)\\}\\)");

    @Override
    public SqlSource createSqlSource(Configuration configuration, String script, Class<?> parameterType) {
        Matcher matcher = inPattern.matcher(script);
        if (matcher.find()) {
            StringBuffer ss = new StringBuffer();
            ss.append("<set>");

            Class<?> historiableEntity = HistoriableEntity.class;

            for (Field field : historiableEntity.getDeclaredFields()) {
                String temp = "<if test=\"__field != null\">__column=#{__field},</if>";
                ss.append(temp.replaceAll("__field", field.getName())
                        .replaceAll("__column", ("C_" + field.getName())));
            }

            for (Field field : parameterType.getDeclaredFields()) {
                if (!field.isAnnotationPresent(Invisible.class)) {
                    String temp = "<if test=\"__field != null\">__column=#{__field},</if>";
                    ss.append(temp.replaceAll("__field", field.getName())
                            .replaceAll("__column", ("C_" + field.getName())));
                }
            }

            ss.deleteCharAt(ss.lastIndexOf(","));
            ss.append("</set>");
            script = matcher.replaceAll(ss.toString());
            script = "<script>" + script + "</script>";
        }
        return super.createSqlSource(configuration, script, parameterType);
    }
}

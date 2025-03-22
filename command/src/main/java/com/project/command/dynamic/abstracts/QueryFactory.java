package com.project.command.dynamic.abstracts;

import java.util.List;

public class QueryFactory {

    public static AbstractQuery from(String type, List<String> args, boolean negate){
        if (type.equals("USER_OF")) {
            return new UserOfQuery(args, negate);
        }
        return null;
    }
}

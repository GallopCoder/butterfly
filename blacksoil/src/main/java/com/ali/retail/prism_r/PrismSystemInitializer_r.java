package com.ali.retail.prism_r;

import cnm.hither.search.prism.PrismSystem;
import cnm.hither.search.prism.conf.PrismConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PrismSystemInitializer_r {

    private static final Logger LOGGER = LoggerFactory.getLogger(PrismSystemInitializer_r.class);

    /**
     * plz put the config file in folder: <System.getProperties(user.dir) + /conf>
     * @param search_system_name
     * @param configFileName
     * @throws Exception
     */
    public static void init(String search_system_name, String configFileName) throws Exception {
        PrismConf cfg = new PrismConf(configFileName);
        LOGGER.info("Complete load config file. Prism_server_name: " + search_system_name + ", configFileName: " + configFileName);
        PrismSystem.init(search_system_name, cfg);
        LOGGER.info("Init Prism server completely. Prism_server_name: " + search_system_name + ", configFileName: " + configFileName);
    }

    public static PrismSystem getPrismSystem(String search_system_name) throws Exception {
        return PrismSystem.getInstance(search_system_name);
    }

    /**
     * plz put the config file in folder: <System.getProperties(user.dir) + /conf>
     * @param configFileName
     * @throws Exception
     */
    public static void init(String configFileName) throws Exception {
        PrismConf cfg = new PrismConf(configFileName);
        PrismSystem.init(cfg);
    }

    public static PrismSystem getPrismSystem() throws Exception {
        return PrismSystem.getInstance();
    }

    /**
     * plz put the config file in folder: <System.getProperties(user.dir) + /conf>
     * @throws Exception
     */
    public static void init() throws Exception {
        PrismConf cfg = new PrismConf();
        PrismSystem.init(cfg);
    }
}

package com.ali.retail.prism;

import cnm.hither.search.prism.PrismSystem;
import cnm.hither.search.prism.conf.PrismConf;
import com.zhongsou.search.core.ISearchException;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class PrismSystemInitializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(PrismSystemInitializer.class);

    /**
     * plz put the config file in folder: <System.getProperties(user.dir) + /conf>
     * @param fname if fname is not empty, just need fill the first index value of fname
     * @throws Exception
     */
    public static void init(String... fname) throws Exception {
        try {
            LOGGER.info("Begin to init Prism System instance.");
            PrismConf cfg = (ArrayUtils.isEmpty(fname)) ? new PrismConf(fname[0]) : new PrismConf();
            LOGGER.info("Complete load config file");
            PrismSystem.init(cfg);
            LOGGER.info("Complete init Prism server");
        } catch (Exception e) {
            throw new ISearchException("Init prism system occur a error, reason - " + e.getMessage());
        }
    }

    /**
     * plz put the config file in folder: <System.getProperties(user.dir) + /conf>
     * @param server_name
     * @param conf_name
     * @throws Exception
     */
    public static void init(String server_name, String conf_name) throws Exception {
        LOGGER.info("Begin to init Prism System instance.");
        if (StringUtils.isBlank(server_name) || StringUtils.isBlank(conf_name))
            throw new Exception("server_name or conf_name cannot be blank, plz check.");
        try{
            PrismConf cfg = new PrismConf(conf_name);
            LOGGER.info("Complete load config file: " + conf_name);
            PrismSystem.init(server_name, cfg);
            LOGGER.info("Complete init Prism server: " + server_name);
        } catch (Exception e) {
            throw new ISearchException("Init prism system occur a error, server_name: " + server_name + ", conf_name: " + conf_name + ", reason - " + e.getMessage());
        }
    }

    /**
     * plz put the config file in folder: <System.getProperties(user.dir) + /conf>
     * @param map <server_name, config_file_name>
     * @throws Exception
     */
    public static void init(Map<String, String> map) throws Exception {
        if(map == null) {
            init(); return;
        }
        for(Map.Entry<String,String> entry : map.entrySet()) {
            init(entry.getKey(), entry.getValue());
        }
    }

    /**
     * get server by server name
     * @param search_system_name server name
     * @return
     * @throws Exception
     */
    public static PrismSystem getPrismSystem(String search_system_name) throws Exception {
        LOGGER.info("Begin to get Prism System instance. server_name: {}", search_system_name);
        return PrismSystem.getInstance(search_system_name);
    }

    /**
     * get server of default server name
     * @return
     * @throws Exception
     */
    public static PrismSystem getPrismSystem() throws Exception {
        LOGGER.info("Begin to get Prism System instance.");
        return PrismSystem.getInstance();
    }


}

package com.ctrip.xpipe.redis.console.dao;

import com.ctrip.xpipe.redis.console.exception.ServerException;
import com.ctrip.xpipe.redis.console.model.ConfigTbl;
import com.ctrip.xpipe.redis.console.model.ConfigTblDao;
import com.ctrip.xpipe.redis.console.model.ConfigTblEntity;
import org.codehaus.plexus.component.repository.exception.ComponentLookupException;
import org.springframework.stereotype.Repository;
import org.unidal.dal.jdbc.DalException;
import org.unidal.lookup.ContainerLoader;

import javax.annotation.PostConstruct;

/**
 * @author wenchao.meng
 *         <p>
 *         Jun 15, 2017
 */
@Repository
public class ConfigDao extends AbstractXpipeConsoleDAO{

    private ConfigTblDao configTblDao;

    @PostConstruct
    private void postConstruct() {
        try {
            configTblDao = ContainerLoader.getDefaultContainer().lookup(ConfigTblDao.class);
        } catch (ComponentLookupException e) {
            throw new ServerException("Cannot construct dao.", e);
        }
    }

    public String getKey(String key) throws DalException {

        ConfigTbl byKey = configTblDao.findByKey(key, ConfigTblEntity.READSET_VALUE);
        return byKey.getValue();
    }

    protected ConfigTbl findByKey(long id) throws DalException {

        return configTblDao.findByPK(id, ConfigTblEntity.READSET_FULL);
    }

    public void setKey(String key, String value) throws DalException {

        ConfigTbl configTbl = new ConfigTbl();
        configTbl.setKey(key);
        configTbl.setValue(value);
        configTblDao.updateByKey(configTbl, ConfigTblEntity.UPDATESET_FULL);
    }


}

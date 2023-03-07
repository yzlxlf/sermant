/*
 * Copyright (C) 2021-2021 Huawei Technologies Co., Ltd. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.huaweicloud.sermant.backend.service.dynamicconfig.kie;

import com.huaweicloud.sermant.backend.service.dynamicconfig.Config;
import com.huaweicloud.sermant.backend.service.dynamicconfig.kie.listener.SubscriberManager;
import com.huaweicloud.sermant.backend.service.dynamicconfig.service.ConfigurationListener;
import com.huaweicloud.sermant.backend.service.dynamicconfig.service.DynamicConfigurationService;
import com.huaweicloud.sermant.backend.service.dynamicconfig.utils.LabelGroupUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * kie配置中心实现
 * <p></p>
 *
 * @author zhouss
 * @since 2021-11-22
 */
public class KieDynamicConfigurationServiceImpl implements DynamicConfigurationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(KieDynamicConfigurationServiceImpl.class);

    private static SubscriberManager subscriberManager;

    private static KieDynamicConfigurationServiceImpl instance;

    private final Map<String, List<String>> groupKeyCache = new ConcurrentHashMap<String, List<String>>();

    private KieDynamicConfigurationServiceImpl() {

    }

    /**
     * 获取实现单例
     *
     * @return KieDynamicConfigurationService
     */
    public static synchronized KieDynamicConfigurationServiceImpl getInstance() {
        if (instance == null) {
            instance = new KieDynamicConfigurationServiceImpl();
            subscriberManager = new SubscriberManager(Config.getInstance().getKieUrl());
        }
        return instance;
    }

    @Override
    public boolean removeGroupListener(String key, String group, ConfigurationListener listener) {
        return updateListener("GroupKey", group, listener, false);
    }

    @Override
    public boolean addGroupListener(String group, ConfigurationListener listener) {
        return updateListener("GroupKey", group, listener, true);
    }

    @Override
    public boolean addConfigListener(String key, String group, ConfigurationListener listener) {
        return updateListener(key, LabelGroupUtils.createLabelGroup(
                Collections.singletonMap(fixSeparator(group, true), fixSeparator(key, false))),
                listener, true);
    }

    @Override
    public boolean removeConfigListener(String key, String group, ConfigurationListener listener) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getConfig(String key, String group) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean publishConfig(String key, String group, String content) {
        return subscriberManager.publishConfig(key, group, content);
    }

    @Override
    public String getDefaultGroup() {
        return "sermant";
    }

    @Override
    public long getDefaultTimeout() {
        return 0;
    }

    @Override
    public List<String> listConfigsFromGroup(String group) {
        return groupKeyCache.get(group);
    }

    /**
     * 更新监听器（删除||添加）
     * 若第一次添加监听器，则会将数据通知给监听器
     *
     * @param key          监听键
     * @param group        分组， 针对KIE特别处理生成group方法{@link LabelGroupUtils#createLabelGroup(Map)}
     * @param listener     对应改组的监听器
     * @param forSubscribe 是否为订阅
     * @return 更新是否成功
     */
    private synchronized boolean updateListener(String key, String group, ConfigurationListener listener, boolean forSubscribe) {
        updateGroupKey(key, group, forSubscribe);
        try {
            if (forSubscribe) {
                return subscriberManager.addGroupListener(group, listener);
            } else {
                return subscriberManager.removeGroupListener(group, listener);
            }
        } catch (Exception exception) {
            LOGGER.warn("Subscribed kie request failed! raw key : " + key);
            return false;
        }
    }

    private void updateGroupKey(String key, String group, boolean forSubscribe) {
        List<String> keys = groupKeyCache.get(group);
        if (keys == null) {
            keys = new ArrayList<>();
        }
        if (forSubscribe) {
            keys.remove(key);
        } else {
            keys.add(key);
        }
        groupKeyCache.put(group, keys);
    }

    /**
     * 去除路径分隔符
     *
     * @param str key or group
     * @param isGroup 是否为组
     * @return 修正值
     */
    private String fixSeparator(String str, boolean isGroup) {
        if (str == null) {
            if (isGroup) {
                // 默认分组
                str = getDefaultGroup();
            } else {
                throw new IllegalArgumentException("Key must not be empty!");
            }
        }
        return str.startsWith("/") ? str.substring(1) : str;
    }
}

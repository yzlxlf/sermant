/*
 * Copyright (C) 2022-2022 Huawei Technologies Co., Ltd. All rights reserved.
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

package com.huaweicloud.sermant.router.config.handler;

import com.huaweicloud.sermant.core.service.dynamicconfig.common.DynamicConfigEvent;
import com.huaweicloud.sermant.core.service.dynamicconfig.common.DynamicConfigEventType;
import com.huaweicloud.sermant.core.utils.StringUtils;
import com.huaweicloud.sermant.router.common.constants.RouterConstant;
import com.huaweicloud.sermant.router.common.utils.CollectionUtils;
import com.huaweicloud.sermant.router.config.cache.ConfigCache;
import com.huaweicloud.sermant.router.config.entity.RouterConfiguration;
import com.huaweicloud.sermant.router.config.entity.Rule;
import com.huaweicloud.sermant.router.config.utils.RuleUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 路由配置处理器（服务维度）
 *
 * @author provenceee
 * @since 2022-08-09
 */
public class ServiceConfigHandler extends AbstractConfigHandler {
    private static final String POINT = ".";

    @Override
    public void handle(DynamicConfigEvent event, String cacheName) {
        RouterConfiguration configuration = ConfigCache.getLabel(cacheName);
        String serviceName = event.getKey().substring(RouterConstant.ROUTER_KEY_PREFIX.length() + 1);
        if (event.getEventType() == DynamicConfigEventType.DELETE) {
            configuration.getRouteRule().remove(serviceName);
            RuleUtils.updateMatchKeys(serviceName, Collections.emptyList());
            return;
        }
        List<Rule> list = JSONArray.parseArray(JSONObject.toJSONString(getRule(event, serviceName)), Rule.class);
        RuleUtils.removeInvalidRules(list);
        if (CollectionUtils.isEmpty(list)) {
            configuration.getRouteRule().remove(serviceName);
        } else {
            list.sort((o1, o2) -> o2.getPrecedence() - o1.getPrecedence());
            configuration.getRouteRule().put(serviceName, list);
        }
        RuleUtils.updateMatchKeys(serviceName, list);
    }

    @Override
    public boolean shouldHandle(String key) {
        return key.startsWith(RouterConstant.ROUTER_KEY_PREFIX + POINT);
    }

    private List<Map<String, Object>> getRule(DynamicConfigEvent event, String serviceName) {
        String content = event.getContent();
        if (StringUtils.isBlank(content)) {
            return Collections.emptyList();
        }
        Map<String, List<Map<String, Object>>> map = yaml.load(content);
        return map.get(RouterConstant.ROUTER_KEY_PREFIX + POINT + serviceName);
    }
}
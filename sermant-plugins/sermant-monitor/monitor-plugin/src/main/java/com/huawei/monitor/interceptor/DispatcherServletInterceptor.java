/*
 * Copyright (C) 2022-2022 Huawei Technologies Co., Ltd. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.huawei.monitor.interceptor;

import com.huawei.monitor.common.MetricCalEntity;
import com.huawei.monitor.util.MonitorCacheUtil;

import com.huaweicloud.sermant.core.plugin.agent.entity.ExecuteContext;
import com.huaweicloud.sermant.core.plugin.agent.interceptor.AbstractInterceptor;

import javax.servlet.http.HttpServletRequest;

/**
 * HTTP拦截定义
 *
 * @author zhp
 * @since 2022-11-01
 */
public class DispatcherServletInterceptor extends AbstractInterceptor {
    private static final String START_TIME = "startTime";

    @Override
    public ExecuteContext before(ExecuteContext context) {
        if (checkContext(context)) {
            return context;
        }
        context.setExtMemberFieldValue(START_TIME, System.currentTimeMillis());
        return context;
    }

    @Override
    public ExecuteContext after(ExecuteContext context) {
        if (checkContext(context) || context.getExtMemberFieldValue(START_TIME) == null) {
            return context;
        }
        HttpServletRequest request = (HttpServletRequest) context.getArguments()[0];
        MetricCalEntity metricCalEntity = MonitorCacheUtil.getMetricCalEntity(request.getRequestURI());
        metricCalEntity.getReqNum().incrementAndGet();
        long startTime = (Long) context.getExtMemberFieldValue(START_TIME);
        metricCalEntity.getConsumeReqTimeNum().addAndGet(System.currentTimeMillis() - startTime);
        metricCalEntity.getSuccessFulReqNum().incrementAndGet();
        return context;
    }

    private static boolean checkContext(ExecuteContext context) {
        return context == null || context.getArguments() == null || context.getArguments().length < 1;
    }

    @Override
    public ExecuteContext onThrow(ExecuteContext context) {
        if (checkContext(context)) {
            return context;
        }
        HttpServletRequest request = (HttpServletRequest) context.getArguments()[0];
        MetricCalEntity metricCalEntity = MonitorCacheUtil.getMetricCalEntity(request.getRequestURI());
        metricCalEntity.getReqNum().incrementAndGet();
        metricCalEntity.getFailedReqNum().incrementAndGet();
        return context;
    }
}

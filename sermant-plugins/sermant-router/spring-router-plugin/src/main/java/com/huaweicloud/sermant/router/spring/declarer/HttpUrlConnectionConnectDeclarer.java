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

package com.huaweicloud.sermant.router.spring.declarer;

import com.huaweicloud.sermant.core.plugin.agent.declarer.AbstractPluginDeclarer;
import com.huaweicloud.sermant.core.plugin.agent.declarer.InterceptDeclarer;
import com.huaweicloud.sermant.core.plugin.agent.matcher.ClassMatcher;
import com.huaweicloud.sermant.core.plugin.agent.matcher.MethodMatcher;

/**
 * 定义JDK 1.8版本的java.net.HttpURLConnection的拦截点信息<br>
 *
 * @author yuzl 俞真龙
 * @since 2022-10-25
 */
public class HttpUrlConnectionConnectDeclarer extends AbstractPluginDeclarer {
    private static final String METHOD_NAME = "connect";

    private static final String INTERCEPTOR_CLASS =
        "com.huaweicloud.sermant.router.spring.interceptor.HttpUrlConnectionConnectInterceptor";

    @Override
    public ClassMatcher getClassMatcher() {
        return ClassMatcher.isExtendedFrom("java.net.HttpURLConnection");
    }

    @Override
    public InterceptDeclarer[] getInterceptDeclarers(ClassLoader classLoader) {
        return new InterceptDeclarer[] {
            InterceptDeclarer.build(MethodMatcher.nameEquals(METHOD_NAME), INTERCEPTOR_CLASS)};
    }
}

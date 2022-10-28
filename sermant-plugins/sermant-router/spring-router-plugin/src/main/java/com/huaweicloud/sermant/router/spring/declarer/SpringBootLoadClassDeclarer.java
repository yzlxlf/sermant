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
import com.huaweicloud.sermant.router.spring.interceptor.SpringBootLoadClassInterceptor;

/**
 * 增强SpringBootApplication类的main方法，用于加载一些特殊的类
 *
 * @author yuzl 俞真龙
 * @since 2022-10-28
 */
public class SpringBootLoadClassDeclarer extends AbstractPluginDeclarer {
    private static final String[] ENHANCE_CLASS = {"org.springframework.boot.autoconfigure.SpringBootApplication"};

    private static final String INTERCEPT_CLASS = SpringBootLoadClassInterceptor.class.getCanonicalName();

    private static final String METHOD_NAME = "main";

    @Override
    public ClassMatcher getClassMatcher() {
        return ClassMatcher.isAnnotatedWith(ENHANCE_CLASS);
    }

    @Override
    public InterceptDeclarer[] getInterceptDeclarers(ClassLoader classLoader) {
        return new InterceptDeclarer[]{
            InterceptDeclarer.build(MethodMatcher.nameEquals(METHOD_NAME).and(MethodMatcher.isStaticMethod()),
                INTERCEPT_CLASS)
        };
    }
}

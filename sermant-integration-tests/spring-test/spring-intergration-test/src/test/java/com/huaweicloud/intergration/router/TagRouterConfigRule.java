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

package com.huaweicloud.intergration.router;

import com.huaweicloud.intergration.common.rule.AbstractTestRule;
import com.huaweicloud.intergration.common.rule.SermantTestType;

import java.util.Set;

/**
 * 路由测试
 *
 * @author provenceee
 * @since 2022-11-14
 */
public class TagRouterConfigRule extends AbstractTestRule {
    @Override
    protected boolean isSupport(Set<SermantTestType> types) {
        return types.contains(SermantTestType.TAG_ROUTER_CONFIG);
    }
}

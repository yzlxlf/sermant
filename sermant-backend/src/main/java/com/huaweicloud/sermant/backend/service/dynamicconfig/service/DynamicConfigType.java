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

package com.huaweicloud.sermant.backend.service.dynamicconfig.service;

/**
 * Enum for DynamicConfigType,
 * Currently support ZooKeeper, Kie, Nop.
 * Probably will support Nacos, etcd in the future.
 *
 *  @author yangyi
 *  @since 2021-12-10
 */
public enum DynamicConfigType {

    /**
     * zookeeper 配置中心
     */
    ZOO_KEEPER,

    /**
     * servicecomb-kie 配置中心
     */
    KIE,

    /**
     * 配置中心无实现
     */
    NOP;

}

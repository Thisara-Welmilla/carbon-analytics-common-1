/*
 * Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.event.simulator.core.internal.generator.random.util;

import com.google.gson.Gson;

import org.json.JSONObject;
import org.wso2.carbon.event.simulator.core.exception.InvalidConfigException;
import org.wso2.carbon.event.simulator.core.internal.bean.CustomBasedAttributeDTO;
import org.wso2.carbon.event.simulator.core.internal.generator.random.RandomAttributeGenerator;
import org.wso2.carbon.event.simulator.core.internal.util.EventSimulatorConstants;

import static org.wso2.carbon.event.simulator.core.internal.util.CommonOperations.checkAvailabilityOfArray;

import java.util.ArrayList;
import java.util.Random;

/**
 * CustomBasedAttrGenerator class is responsible for generating attribute values from the data list provided by user
 * This class implements interface RandomAttributeGenerator
 */
public class CustomBasedAttrGenerator implements RandomAttributeGenerator {
    private CustomBasedAttributeDTO customBasedAttrConfig = new CustomBasedAttributeDTO();

    public CustomBasedAttrGenerator() {
    }

    /**
     * validateAttributeConfiguration() validates the attribute configuration provided for custom data based random
     * simulation
     *
     * @param attributeConfig attribute configuration for custom data based random simulation
     * @throws InvalidConfigException if a custom data list is not provided
     */
    @Override
    public void validateAttributeConfiguration(JSONObject attributeConfig) throws InvalidConfigException {
        if (!checkAvailabilityOfArray(attributeConfig, EventSimulatorConstants.CUSTOM_DATA_BASED_ATTRIBUTE_LIST)) {
            throw new InvalidConfigException("Data list is not given for " +
                    RandomDataGeneratorType.CUSTOM_DATA_BASED + " simulation. Invalid " +
                    "attribute configuration provided : " + attributeConfig.toString());
        }
    }

    /**
     * createRandomAttributeDTO() creates a CustomBasedAttributeDTO for custom attribute generator
     *
     * @param attributeConfig is the attribute configuration
     */
    @Override
    public void createRandomAttributeDTO(JSONObject attributeConfig) {
        Gson gson = new Gson();
        customBasedAttrConfig.setCustomData(gson.fromJson(attributeConfig.
                getJSONArray(EventSimulatorConstants.CUSTOM_DATA_BASED_ATTRIBUTE_LIST).toString(), ArrayList.class));
    }

    /**
     * generateAttribute() generate data with in given data list
     *
     * @return generated data from custom data list
     */
    @Override
    public Object generateAttribute() {
        /*
         * randomElementSelector will be assigned a pseudoRandom integer value from 0 to (datalist.length - 1)
         * the data element in the randomElementSelector's position will be assigned to result and returned
         * */
        Random random = new Random();
        int randomElementSelector = random.nextInt(customBasedAttrConfig.getCustomDataList().size());
        return customBasedAttrConfig.getCustomDataList().get(randomElementSelector);
    }

    /**
     * getAttributeConfiguration(0 returns attribute configuration used for custom based attribute configuration
     *
     * @return attribute configuration
     */
    @Override
    public String getAttributeConfiguration() {
        return customBasedAttrConfig.toString();
    }


}

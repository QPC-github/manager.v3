// Copyright (C) 2006 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.enterprise.connector.instantiator;

import com.google.enterprise.connector.instantiator.InstanceInfo;
import com.google.enterprise.connector.instantiator.InstanceMap;
import com.google.enterprise.connector.instantiator.InstantiatorException;
import com.google.enterprise.connector.instantiator.TypeMap;
import com.google.enterprise.connector.test.ConnectorTestUtils;
import com.google.enterprise.connector.test.JsonObjectAsMap;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Map;

/**
 * 
 */
public class InstanceMapTest extends TestCase {

  /**
   * Test method for
   * {@link com.google.enterprise.connector.instantiator.InstanceMap
   * #getInstanceInfo(java.lang.String)}.
   * 
   * @throws JSONException
   * @throws InstantiatorException
   */
  public final void testGetInstanceInfo() throws JSONException,
      InstantiatorException {
    TypeMap typeMap =
        new TypeMap("classpath*:config/connectorType.xml",
            "testdata/instantiatorTests");
    InstanceMap instanceMap = new InstanceMap(typeMap);
    Assert.assertEquals(0, instanceMap.size());

    {
      /**
       * Test creation of a connector of type TestConnectorA. The type should
       * already have been created.
       */
      String name = "connector1";
      String typeName = "TestConnectorA";
      String jsonConfigString =
          "{username:foo, password:bar, color:red, "
              + "RepositoryFile:MockRepositoryEventLog3.txt}";
      updateConnectorTest(instanceMap, name, typeName, jsonConfigString);
    }

    {
      /**
       * Test creation of a connector of type TestConnectorB. The type should
       * already have been created.
       */
      String name = "connector2";
      String typeName = "TestConnectorB";
      String jsonConfigString =
          "{username:foo, password:bar, flavor:minty-fresh, "
              + "RepositoryFile:MockRepositoryEventLog3.txt}";
      updateConnectorTest(instanceMap, name, typeName, jsonConfigString);
    }

    Assert.assertEquals(2, instanceMap.size());

    {
      /**
       * Test update of a connector instance of type TestConnectorB. The
       * instance was created in an earlier test.
       */
      String name = "connector2";
      String typeName = "TestConnectorB";
      String jsonConfigString =
          "{username:foo, password:bar, flavor:butterscotch, "
              + "RepositoryFile:MockRepositoryEventLog2.txt}";
      updateConnectorTest(instanceMap, name, typeName, jsonConfigString);
    }

    Assert.assertEquals(2, instanceMap.size());

    {
      /**
       * Test update of a connector instance of type TestConnectorA. The
       * instance was created in an earlier test.
       */
      String name = "connector3";
      String typeName = "TestConnectorA";
      String jsonConfigString =
          "{username:foo, password:bar, color:blue, "
              + "RepositoryFile:MockRepositoryEventLog2.txt}";
      updateConnectorTest(instanceMap, name, typeName, jsonConfigString);
    }

    Assert.assertEquals(3, instanceMap.size());

    {
      /**
       * Test creation of a connector second instance of type TestConnectorB.
       */
      String name = "connector3";
      String typeName = "TestConnectorB";
      String jsonConfigString =
          "{username:foo, password:bar, flavor:chocolate, "
              + "RepositoryFile:MockRepositoryEventLog2.txt}";
      updateConnectorTest(instanceMap, name, typeName, jsonConfigString);
    }

    Assert.assertEquals(3, instanceMap.size());

    instanceMap.dropConnector("connector1");
    instanceMap.dropConnector("connector2");
    instanceMap.dropConnector("connector3");

    Assert.assertEquals(0, instanceMap.size());

  }

  private void updateConnectorTest(InstanceMap instanceMap, String name,
      String typeName, String jsonConfigString) throws JSONException,
      InstantiatorException {
    Map config = new JsonObjectAsMap(new JSONObject(jsonConfigString));
    instanceMap.updateConnector(name, typeName, config);
    InstanceInfo instanceInfo = instanceMap.getInstanceInfo(name);
    File connectorDir = instanceInfo.getConnectorDir();
    Assert.assertTrue(connectorDir.exists());
    Assert.assertEquals(name, instanceInfo.getName());
    ConnectorTestUtils.compareMaps(config, instanceInfo.getProperties(),
        "input config", "returned config");
  }
}
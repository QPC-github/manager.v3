// Copyright (C) 2006 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.enterprise.connector.manager;

import com.google.enterprise.connector.persist.ConnectorNotFoundException;
import com.google.enterprise.connector.persist.ConnectorTypeNotFoundException;
import com.google.enterprise.connector.persist.PersistentStoreException;
import com.google.enterprise.connector.spi.ConfigureResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

/**
 * A mock implementation for the Manager interface. This implementation
 * basically hardcodes a bunch of responses and can be used before the actual
 * implementation is complete.
 * 
 * TODO: move to javatests when possible.
 * 
 */
public class MockManager implements Manager {
  private static final MockManager INSTANCE = new MockManager();
  private static final Logger LOG =
      Logger.getLogger(MockManager.class.getName());

  private MockManager() {
  }

  public static MockManager getInstance() {
    return INSTANCE;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.enterprise.connector.manager.Manager#authorizeTokens(java.lang.String,
   *      java.util.List, java.lang.String)
   */
  public List authorizeTokens(String connectorName, List tokenList,
      String username) {
    List tokenAuthList = new ArrayList();
    for (int i = 0; i < tokenList.size(); i++) {
      tokenAuthList.add(Boolean.valueOf(true));
    }
    return tokenAuthList;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.enterprise.connector.manager.Manager#authenticate(java.lang.String,
   *      java.lang.String, java.lang.String)
   */
  public boolean authenticate(String connectorName, String username,
      String password) {
    return true;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.enterprise.connector.manager.Manager#authorizeDocids(java.lang.String,
   *      java.util.List, java.lang.String)
   */
  public List authorizeDocids(String connectorName, List docidList,
      String username) {
    List docidAuthList = new ArrayList();
    for (int i = 0; i < docidList.size(); i++) {
      docidAuthList.add(Boolean.valueOf(true));
    }
    return docidAuthList;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.enterprise.connector.manager.Manager#getConnectorTypes()
   */
  public List getConnectorTypes() {
    return Arrays.asList(new String[]{"Documentum", "Sharepoint", "Filenet"});
  }

  public void storeConfig(boolean certAuth, String feederGateHost,
      int feederGatePort, int maxFeedRate) {
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.enterprise.connector.manager.Manager#getConfigForm(java.lang.String,
   *      java.lang.String)
   */
  public ConfigureResponse getConfigForm(String connectorTypeName, String language)
      throws ConnectorTypeNotFoundException {
    String message = "Sample form for " + connectorTypeName + "lang " + language;
    String formSnippet = 
      "    <tr><td>Repository</td>" +
      "      <td><input type=\"text\" name=\"repository\" value=\"\"></td>" +
      "    </tr>" +
      "    <tr><td>Username</td>" +
      "      <td><input type=\"text\" name=\"username\" value=\"\">" +
      "      </td></tr>" +
      "    <tr><td>Password</td>" +
      "      <td><input type=\"password\" name=\"passwd\" value=\"\">" +
      "    </td></tr>" +
      "    <tr><td>Seed URIs</td>" + 
      "      <td><textarea name=\"seedUris\"></textarea></td></tr>";
    return new ConfigureResponse(message, formSnippet);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.enterprise.connector.manager.Manager#getConfigFormForConnector(java.lang.String,
   *      java.lang.String)
   */
  public ConfigureResponse getConfigFormForConnector(String connectorName,
      String language) throws ConnectorNotFoundException {
    // have caller use default form by returning null
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.enterprise.connector.manager.Manager#getConnectorStatus(java.lang.String)
   */
  public ConnectorStatus getConnectorStatus(String connectorName) {
    String name = connectorName;
    String type = "Documentum";
    int status = 0;
    return new ConnectorStatus(name, type, status);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.enterprise.connector.manager.Manager#getConnectorStatuses()
   */
  public List getConnectorStatuses() {
    List statuses = new ArrayList();
    statuses.add(getConnectorStatus("foo"));
    return statuses;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.enterprise.connector.manager.Manager#setConfig(java.lang.String,
   *      java.util.Map, java.lang.String)
   */
  public ConfigureResponse setConfig(String connectorName, Map configData,
      String language) throws ConnectorNotFoundException,
      ConnectorManagerException {
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.enterprise.connector.manager.Manager#setConfig(java.lang.String,
   *      java.util.Map, java.lang.String)
   */
  public ConfigureResponse setConnectorConfig(String connectorName,
      Map configData, String language) throws ConnectorNotFoundException,
      PersistentStoreException {
    LOG.info("setConnectorConfig() connectorName: " + connectorName);
    LOG.info("configData: ");
    Set set = configData.entrySet();
    Iterator iterator = set.iterator();
    while (iterator.hasNext()) {
      Map.Entry entry = (Map.Entry) iterator.next();
      LOG.info(entry.getKey() + "/" + entry.getValue());
    }
    // null is a success response
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.enterprise.connector.manager.Manager#storeConfig(boolean,
   *      java.lang.String, int, int)
   */
  public void setConnectorManagerConfig(boolean certAuth,
      String feederGateHost, int feederGatePort, int maxFeedRate)
      throws PersistentStoreException {
    // do nothing
  }

}
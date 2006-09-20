// Copyright 2006 Google Inc.  All Rights Reserved.
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

package com.google.enterprise.connector.persist;

/**
 * Interface describing the persistence needs of a Scheduler.
 *
 */
public interface ConnectorScheduleStore {
  /**
   * Gets the stored schedule of a named connector.
   * 
   * @param connectorName
   * @return the schedule String, or null if no schedule has been stored for this connector
   */
  public String getConnectorSchedule(String connectorName);

  /**
   * Sets the stored schedule of a named connector.
   * 
   * @param connectorName
   * @param connectorSchedule String to store
   */
  public void storeConnectorSchedule(String connectorName, 
      String connectorSchedule);
  
}
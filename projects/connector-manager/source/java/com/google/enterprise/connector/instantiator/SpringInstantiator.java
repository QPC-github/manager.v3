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

import com.google.enterprise.connector.persist.ConnectorNotFoundException;
import com.google.enterprise.connector.persist.ConnectorTypeNotFoundException;
import com.google.enterprise.connector.spi.ConnectorType;
import com.google.enterprise.connector.traversal.Traverser;

/**
 * 
 */
public class SpringInstantiator implements Instantiator {

  // dependencies
  SpringConnectorTypeInstantiator springConnectorTypeInstantiator;
  SpringConnectorInstantiator springConnectorInstantiator;

  /**
   * @param springConnectorTypeInstantiator
   * @param springConnectorInstantiator
   */
  public SpringInstantiator(
      SpringConnectorTypeInstantiator springConnectorTypeInstantiator,
      SpringConnectorInstantiator springConnectorInstantiator) {
    this.springConnectorTypeInstantiator = springConnectorTypeInstantiator;
    this.springConnectorInstantiator = springConnectorInstantiator;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.enterprise.connector.instantiator.Instantiator#getConnectorType(java.lang.String)
   */
  public ConnectorType getConnectorType(String connectorTypeName)
      throws ConnectorTypeNotFoundException {
    return springConnectorTypeInstantiator.getConnectorType(connectorTypeName);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.enterprise.connector.instantiator.Instantiator#getTraverser(java.lang.String)
   */
  public Traverser getTraverser(String connectorName)
      throws ConnectorNotFoundException, InstantiatorException {
    return springConnectorInstantiator.getTraverser(connectorName);
  }

}
===================================
Ubidots Java API Client
===================================

The Ubidots Java API Client makes calls to the `Ubidots API <http://things.ubidots.com/api>`_.  For more information about Ubidots, visit `http://ubidots.com <http://ubidots.com>`_.


Dependencies
-----------------------------

Ubidots for Java is available as an unbundled jar, with the following dependencies:


* `Gson <http://code.google.com/p/google-gson/>`_, the open-source Google JSON library
* `Apache HttpCore <http://hc.apache.org/downloads.cgi>`_, Apache's open-source implementation of HTTP
* `Apache HttpClient <http://hc.apache.org/downloads.cgi>`_, Apache's open-source implementation of an HTTP agent
* `Apache Commons - Logging <http://commons.apache.org/proper/commons-logging/>`_, a dependency of Apache HttpClient (no logging is actually used)


For the latest version of the dependencies, follow the above links. To download the latest versions *at the time of writing*, use the commands below:

.. code-block:: bash

    $ wget http://google-gson.googlecode.com/files/google-gson-2.2.4-release.zip
    $ wget http://apache.claz.org//httpcomponents/httpcore/binary/httpcomponents-core-4.3-bin.tar.gz
    $ wget http://apache.claz.org//httpcomponents/httpclient/binary/httpcomponents-client-4.3-bin.tar.gz
    $ wget http://mirror.sdunix.com/apache//commons/logging/binaries/commons-logging-1.1.3-bin.tar.gz


Connecting to the API
----------------------

Before playing with the API you must be able to connect to it using your private API key, which can be found `in your profile <http://app.ubidots.com/userdata/api/>`_.

If you don't have an account yet, you can `create one here <http://app.ubidots.com/accounts/signup/>`_.

Once you have your API key, you can connect to the API by creating an ApiClient instance. First, import all classes from the com.ubidots package:

.. code-block:: java

    import com.ubidots.*;

Let's assume your API key is: "7fj39fk3044045k89fbh34rsd9823jkfs8323" then your code would look like this:

.. code-block:: java

    api = new ApiClient("7fj39fk3044045k89fbh34rsd9823jkfs8323");

Now you have an instance of the ApiClient class ("api") which can be used to connect to the API service.


Creating a DataSource
----------------------

As you might know by now, a data source represents a device or a virtual source.

This line creates a new data source:

.. code-block:: java

    dataSource = api.createDatasource("myNewDs");


This new data source can be used to track different variables, so let's create one.


Creating a Variable
--------------------

A variable is a time-series containing different values over time. Let's create one:


.. code-block:: java

    variable = dataSource.createVariable("myNewVar");


Now you have a new variable, so let's create a new value for this variable.


Saving a new Value to a Variable
--------------------------------

Given the instantiated variable, you can save a new value with the following line:

.. code-block:: java

    variable.saveValue(10);

Unlike the Python library, the timestamp for the value is automatically created for the value on the client-side.

Getting Values
--------------

To get the values for a variable, use the method getValues() in an instance of the class Variable.

.. code-block:: java

    Value[] values = variable.getValues();


Getting all the Data sources
-----------------------------

If you want to get all your data sources you can use the instance of the API directly:

.. code-block:: java

    DataSource[] dataSources = api.getDataSources();


Getting a specific Data source
------------------------------

Each data source has a unique id that tells the server which one to retrieve.

For example, if a data source has the id 51c99cfdf91b28459f976414, it can be retrieved using the method getDatasource(String) of the ApiClient instance:


.. code-block:: java

    mySpecificDataSource = api.getDataSource("51c99cfdf91b28459f976414");


Getting All Variables from a Data source
-----------------------------------------

You can also retrieve all the variables of a data source:

.. code-block:: java

    Variable[] allDataSourceVariables = dataSource.getVariables();


Getting a specific Variable
------------------------------

As with data sources, use your variable's id to retrieve the details about a variable:

.. code-block:: java

    mySpecificVariable = api.getVariable("56799cf1231b28459f976417");

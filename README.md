# Project-Devops_Test1206
Project-Devops_Test1206
### Before getting started

This doc covers installation guidelines for installing the docker engine and a private docker registry. Docker engine is
required everywhere you want to use docker. But steps for the private docker registry are only required in Aliyun box in
our case, because we want to use Aliyun as a docker hub through which docker images can be stored and shared for
development and integration testing purpose.

For production deployment, we will need to select one of the customer servers to deploy a private docker registry. The
steps of installing a private docker registry are pretty much the same, except for some environment specific variables,
such as IP addresses.

**IMPORTANT:**
The version of default docker engine that comes with Ubuntu 18.04 is a bit outdated, as such it needs to be uninstalled.
The detailed steps for uninstalling docker can be found [here](https://docs.docker.com/engine/install/ubuntu/).

### Setting up a docker environment for development

#### 1. Install docker engine in your ubuntu box, if not already done.

For detailed installation steps, please go [here](https://docs.docker.com/engine/install/ubuntu/).

#### 2. Install docker-compose

Please follow this [link](https://docs.docker.com/compose/install/) to install docker-compose.

#### 3. Import self-signed ssl certificate

The self-signed certificate is generated in the aliyuan box: 20.185.38.158. Execute the following command lines to
import the certificate:

```
mkdir -p /etc/docker/certs.d/20.185.38.158:5000
cp /home/kafka/workspace/nextgen/devops/docker/registry/docker_reg_certs/ca.crt \
  /etc/docker/certs.d/20.185.38.158:5000/
#reload docker daemon to use the certificate
sudo service docker reload
```

#### 4. Test the docker installation

```
docker login https://20.185.38.158:5000/
# username: nextgen
# password: will be shared via teams chat
# pull a docker image from the private docker registry
docker pull 20.185.38.158:5000/ordersservice
```

#### 5. Try to build a docker image for a microservice

```
# we take ordersservice as an example
cd ../apps/ordersservice
docker build -t microsservice .
# this will show a list of local docker images
docker images
# push the newly built image to the aliyuan docker registry
docker tag ordersservice 20.185.38.158:5000/ordersservice
# the image should be uploaded to the private docker registry 
# if the following commandline is successfully run
docker push 20.185.38.158:5000/ordersservice
```

The [Dockfile](https://github.com/zjlhxq/nextgen/tree/masteel/apps/ordersservice/Dockerfile)
and [docker-compose.yml](https://github.com/zjlhxq/nextgen/tree/masteel/apps/ordersservice/docker-compose.yml) files
used in the above example can be seen by clicking on the links.

#### 6. Test run the newly built docker image in your local env

```
cd ../apps/ordersservice
docker-compose up
# you can stop the running application by ctrl+c
# And run it as daemon
docker-compose up -d
docker ps
CONTAINER ID   IMAGE     COMMAND   CREATED   STATUS    PORTS     NAMES
docker-compose  up -d
Starting ordersservice_app_1 ... done
docker ps
CONTAINER ID   IMAGE           COMMAND                  CREATED       STATUS         PORTS     NAMES
055d05e5612c   ordersservice   "java org.springfram…"   7 hours ago   Up 5 seconds             ordersservice_app_1
docker stop 055d05e5612c
055d05e5612c
docker ps
CONTAINER ID   IMAGE     COMMAND   CREATED   STATUS    PORTS     NAMES

```

### Setting up a private docker registry

Note: The following content is for installing a private docker registry service and this is intended to be followed by
someone who is going to setup a private docker registry. For those not interested in this, you can safely skip reading
the content below.

Hosting a private docker registry is very useful for integration testing during development and later on for production
deployment.

#### 1. Install docker engine by performing step#1&2 mentioned in the above section

#### 2. Create self-signed SSL certificate

~~Before executing the below openssl command line to generate the certificate, add your IP in subjectAltName in the
openssl.cnf by searching for subjectAltName and replacing the value after 'subjectAltName=IP:' in the section [v3_ca]in
/etc/ssl/openssl.cnf:~~

```
sudo vim /etc/ssl/openssl.cnf
subjectAltName=IP:8.131.254.254 in /etc/ssl/openss.cnf
```

~~The IP should be an external IP if this is done in Aliyun box, regular IP address if in on-premise network environment
like the customer production environment.~~

```
mkdir -p ~/docker/docker_reg_certs
openssl req   -newkey rsa:4096 -nodes -sha256 -keyout ~/docker/docker_reg_certs/domain.key -x509 -days 36500  \
-subj "/C=CN/ST=BJ/O=SMS/CN=docker.nextgen.io" \
-addext "subjectAltName=DNS:docker.nextgen.io" \
-out ~/docker/docker_reg_certs/domain.crt
```

### 3. Import the SSL certificate

Once the certificate is generated, you can import it by following these steps:

```
sudo mkdir -p /etc/docker/certs.d/docker.registry.nextgen.io:5000
sudo cp ~/docker/docker_reg_certs/domain.crt /etc/docker/certs.d/docker.registry.nextgen.io:5000/ca.crt
#reload docker daemon to use the certificate
sudo service docker reload
```

#### 4. Create login credentials

The login credentials are required because otherwise everyone that has access to the ca.crt would be able to push images
to the docker registry. We have to enforce access control with a minimum basic authentication, because this is the
easiest option for us, given that we do have an enterprise-level identity manager available for us to use.

##### Prerequisites

The htpasswd is needed to generate the credentials, so if it is not available in your ubuntu box, please just install
it:

```
sudo apt update
sudo apt install apache2-utils
```

##### Procedure

```
# create a directory to store the htpasswd file
sudo mkdir -p /var/lib/docker-registry/auth
# create the login credentials, nextgen is the user name and 
# you'll be prompted for a password
sudo htpasswd -c /var/lib/docker-registry/auth/htpasswd nextgen
# check the content of the htpasswd file
sudo cat /var/lib/docker-registry/auth/htpasswd
```

#### 5. Spin up the docker registry

Now that everything seems to be ready, let us try to spin up the registry. Not quite yet, we still need to create a
couple of directories before we can call a day.

```
sudo mkdir -p /var/lib/docker-registry/data
sudo mkdir -p /var/lib/docker-registry/certs
# copy the certificate and key over 
sudo cp ~/docker/docker_reg_certs/domain.* /var/lib/docker-registry/certs
```

Now let us spin up the docker registry service

```
# the docker-compose.yml is located in the below directory
cd ~/workspace/nextgen/devops/docker/registry
# start up the docker registry service
docker-compose up -d
```

The docker-compose.yml is made available [here](./docker-compose.yml), you can check out the details by clicking that
link.


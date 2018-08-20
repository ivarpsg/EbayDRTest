package com.ebay.cart.pages;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.cloudformation.AmazonCloudFormation;
import com.amazonaws.services.cloudformation.AmazonCloudFormationClient;
import com.amazonaws.services.cloudformation.AmazonCloudFormationClientBuilder;
import com.amazonaws.services.cloudformation.model.CreateStackRequest;
import com.amazonaws.services.cloudformation.model.CreateStackResult;
import com.amazonaws.services.cloudformation.model.DeleteStackRequest;
import com.amazonaws.services.cloudformation.model.DeleteStackResult;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.*;
import com.ebay.cart.Base.BrowserManager;
import com.ebay.cart.Base.Log;
import com.ebay.cart.Base.Utils;

import java.util.ArrayList;
import java.util.List;

public class AWSPage extends BrowserManager {

    //Assign values from the test classes or a configuration file
    public String ACCESS_KEY_ID = "//Add your subscription's access key here";
    public String SECRET_KEY = "//Add your subscription's secret key here";

    //Amazon Regions that are currently supported by CLOUD MATRIX
    //Default Region to be used if no region is mentioned arguments
    private final String US_EAST_VIRGINIA =	"us-east-1";
    private final String US_EAST_OHIO = "us-east-2";
    private final String US_WEST_CALIFORNIA = "us-west-1";
    private final String US_WEST_OREGON = "us-west-2";
    private final String CANADA_CENTRAL = "ca-central-1";
    private final String ASIA_PACIFIC_SEOUL = "ap-northeast-2";
    private final String ASIA_PACIFIC_SINGAPORE = "ap-southeast-1";
    private final String ASIA_PACIFIC_SYDNEY = "ap-southeast-2";
    private final String ASIA_PACIFIC_TOKYO = "ap-northeast-1";
    private final String EU_FRANKFURT = "eu-central-1";
    private final String EU_IRELAND = "eu-west-1";
    private final String EU_LONDON = "eu-west-2";
    private final String SOUTH_AMERICA = "sa-east-1";

    //Amazon Region that is not supported by CLOUD MATRIX
    private final String ASIA_PACIFIC_MUMBAI = "ap-south-1";

    //Regions array. To randomly select a region
    private final String REGIONS[]={ "us-east-1",	"us-east-2", "us-west-1", "us-west-2", "ca-central-1",
            "ap-south-1", "ap-northeast-2", "ap-southeast-1", "ap-southeast-2",
            "ap-northeast-1", "eu-central-1", "eu-west-1", "eu-west-2", "sa-east-1"};

    //Default AMI-IMAGE-ID's based on the default region
    public String AMI_IMAGE_ID = "ami-0442c32465d332b4a";

    //Default InstanceType
    public InstanceType T2Micro;

    //VPC and Subnet
    public String DEFAULT_VPC = "";
    public String DEFAULT_SUBNET = "";

    //Exception Handling Options. Turn these true and false to handle exceptions
    //By default these options are enabled. Create object in the calling class and set it to false
    public boolean useDefaultVPC = true;
    public boolean useDefaultRegion = true;
    public boolean useDefaultKeyPair = true;

    /* ****************************************************************************** */
    /**
     * This method created an AWS Client and establishes a connection with AWS
     * @return ec2 AWS Client object*/
    public AmazonEC2 createEC2Client(String region){
        //access key check
        if (ACCESS_KEY_ID==null){
            Log.error("ACCESS KEY cannot be null");
        }
        //secret key check
        if (SECRET_KEY==null){
            Log.error("SECRET KEY cannot be null");
        }
        //region check
        if (region==null){
            Log.info("Region cannot be null. Using default region and its configuration to create instance");
            region = ASIA_PACIFIC_SYDNEY;
        }
        try{
            //Load the credentials and create the AWS client
            AWSCredentials amazonCreds = new BasicAWSCredentials(ACCESS_KEY_ID,SECRET_KEY);
            AmazonEC2 ec2 = AmazonEC2ClientBuilder.standard()
                    .withRegion(region)
                    .withCredentials(new AWSStaticCredentialsProvider(amazonCreds))
                    .build();
            return ec2;

        }
        catch(AmazonServiceException ase){
            Log.error("EXCEPTION FOUND: \n"+ase.getErrorMessage());
            return null;
        }
    }

    /* ****************************************************************************** */
    /**
     * This method created an AWS Client and establishes a connection with AWS
     * @return ec2 AWS Client object*/
    public AmazonEC2 createEC2Client(String accessKey, String secretKey, String region){
        try{
            //access key check
            if (accessKey==null){
                Log.error("ACCESS KEY cannot be null");
            }
            //secret key check
            if (secretKey==null){
                Log.error("SECRET KEY cannot be null");
            }
            //region check
            region = determineRegion(region);
            if (region==null){
                Log.info("Region cannot be null. Using default region and its configuration to create instance");
                region = ASIA_PACIFIC_SYDNEY;
            }
            //Load the credentials and create the AWS client
            AWSCredentials amazonCreds = new BasicAWSCredentials(accessKey,secretKey);
            AmazonEC2 ec2 = AmazonEC2ClientBuilder.standard()
                    .withRegion(region)
                    .withCredentials(new AWSStaticCredentialsProvider(amazonCreds))
                    .build();
            return ec2;
        }
        catch(AmazonServiceException ase){
            Log.error("EXCEPTION FOUND: \n"+ase.getErrorMessage());
            return null;
        }
    }

    /* ****************************************************************************** */
    /**
     * This methods determines the region where the AWS instance needs to be created based on natural text
     * @return region region code*/
    private String determineRegion(String region) {
        if((region.toLowerCase()).contains("singapore")){
            region = ASIA_PACIFIC_SINGAPORE;
        }
        else if((region.toLowerCase()).contains("sydney")){
            region = ASIA_PACIFIC_SYDNEY;
        }
        else if((region.toLowerCase()).contains("tokyo")){
            region = ASIA_PACIFIC_TOKYO;
        }
        else if((region.toLowerCase()).contains("frankfurt")){
            region = EU_FRANKFURT;
        }
        else if((region.toLowerCase()).contains("ireland")){
            region = EU_IRELAND;
        }
        else if((region.toLowerCase()).contains("(paulo")){
            region = SOUTH_AMERICA;
        }
        else if((region.toLowerCase()).contains("virginia")){
            region = US_EAST_VIRGINIA;
        }
        else if((region.toLowerCase()).contains("california")){
            region = US_WEST_CALIFORNIA;
        }
        else if((region.toLowerCase()).contains("oregon")){
            region = US_WEST_OREGON;
        }
        else{
            Log.info("No match found");
            region = null;
        }
        return region;
    }

    /* ****************************************************************************** */
    /**
     * Creates an AWS instance with the provided combination.
     * @param ec2 AmazonEC2 client Object.
     * @param instanceName Name for the instance.
     * @param amiImageId AMI-Image_Id to be launched.
     * @param type instance type ex.t2.micro, t2.large, t2.nano and so on.
     * @param keyPairName keyname to which the instance needs to be associated.
     * @param maxNumOfInstaces The maximum number of instances to launch.
     * @param minNumOfInstances The minimum number of instances to launch.
     * @param subnetID The ID of the subnet to launch the instance into.
     * @return instanceID list of instance ID's that were created
     * */
    public List<String> createEC2Instance(AmazonEC2 ec2, String instanceName, String amiImageId, String type, String keyPairName, int minNumOfInstances, int maxNumOfInstaces, String subnetID, List<Tag> tags) {
        List<String> instanceID = new ArrayList<String>();
        try{
            //Create AWS EC2 instance
            RunInstancesRequest instanceRequest = new RunInstancesRequest()	.withImageId(amiImageId)
                    .withInstanceType(InstanceType.T2Micro)
                    .withSubnetId(subnetID)
                    .withMaxCount(maxNumOfInstaces)
                    .withMinCount(minNumOfInstances);

            RunInstancesResult instanceResponse = ec2.runInstances(instanceRequest);
            //Need this ID if you want to delete this specific resource.
            for (int i=0; i<maxNumOfInstaces; i++){
                String instanceId = instanceResponse.getReservation().getInstances().get(i).getInstanceId();
                //add the instance id to the list, to be later used for deletion
                Log.info("Created the instance "+instanceId);
                instanceID.add(instanceId);
                //Adding tags to the instance
                CreateTagsRequest tag_request = new CreateTagsRequest() .withTags(tags)
                        .withResources(instanceId);
                CreateTagsResult tag_response = ec2.createTags(tag_request);
                Log.info("Added Tag "+instanceName);
            }
        }
        catch(Exception ase){
            System.out.println("EXCEPTION FOUND createEC2Instance: \n"+ase);

        }
        return instanceID;
    }

    /* ****************************************************************************** */
    /**
     * Delete an AWS EC2 instance based on the list of instanceID's provided
     * @param ec2 AmazonEC2 object
     * @param instanceID List of instance ID's to be deleted
     * */
    public void deleteEC2Instance(AmazonEC2 ec2, List<String> instanceID){
        try{
            //Delete AWS EC2 instance
            TerminateInstancesRequest deleteRequest = new TerminateInstancesRequest(instanceID);
            TerminateInstancesResult deleteResult = ec2.terminateInstances(deleteRequest);
            List<InstanceStateChange> terminatedInstances = deleteResult.getTerminatingInstances();
            int length = (terminatedInstances).size();
            for (int i=0; i<length; i++){
                Log.info("Terminated Instance: "+terminatedInstances.get(i));
            }
        }
        catch(AmazonServiceException ase){
            Log.error("EXCEPTION FOUND deleteEC2Instance: \n"+ase.getErrorMessage());
        }
    }

    /* ****************************************************************************** */
    /**
     * This method creates a new key pair name
     * @param ec2 AmazonEC2 instance with preloaded credentials
     * @param keyPairName name used for the keyPair
     * */
    public String createEC2KeyPair(AmazonEC2 ec2, String keyPairName){
        if(keyPairName==null){
            Log.info("Key Pair Name cannot be null. Hence using randomly generated Key Pair name");
            Utils utils=new Utils();
            keyPairName = "amzKey"+utils.getRandomStr(3);
        }
        try{
            CreateKeyPairRequest request = new CreateKeyPairRequest().withKeyName(keyPairName);
            CreateKeyPairResult response = ec2.createKeyPair(request);
            Log.info("Successfully created "+response.getKeyPair().getKeyName());
            return response.getKeyPair().getKeyName();
        }
        catch (AmazonServiceException e){
            Log.error("EXCEPTION FOUND createEC2KeyPair: \n"+e.getErrorMessage());
            if (e.getErrorMessage().contains("The keypair '"+keyPairName+"' already exists")){
                Log.info("KeyPair already exisiting. Returning the existing keypair name");
                return keyPairName;
            }
            else{
                return null;
            }
        }
    }

    /* ****************************************************************************** */
    /**
     * This method deletes a new key pair name
     * @param ec2 AmazonEC2 instance with preloaded credentials
     * @param keyPairName name of the keypair to be deleted
     * @throws Exception
     * */
    public void deleteEC2KeyPair(AmazonEC2 ec2, String keyPairName) throws Exception{
        if(keyPairName==null){
            Log.error("KeyPairName cannot be null");
            throw new Exception("KeyPairName cannot be null");
        }
        try{
            DeleteKeyPairRequest request = new DeleteKeyPairRequest().withKeyName(keyPairName);
            DeleteKeyPairResult response = ec2.deleteKeyPair(request);
            Log.info("Deleted the key Pair "+ keyPairName);
        }
        catch (AmazonServiceException ase){
            Log.error("EXCEPTION FOUND deleteEC2KeyPair: \n"+ase.getErrorMessage());
        }
    }

    /* ****************************************************************************** */
    /**
     * @param ec2 AmazonEC2 client
     * @return vpcID The VPC ID to be used to create Subnet
     * */
    public String createEC2VPC(AmazonEC2 ec2, String cidrBlock){
        String vpcID=null;
        try{
            CreateVpcRequest request = new CreateVpcRequest().withCidrBlock(cidrBlock);
            CreateVpcResult result = ec2.createVpc(request);
            vpcID = result.getVpc().getVpcId();
            Log.info("Created VPC with ID: "+vpcID);
        }
        catch (AmazonEC2Exception ase){
            Log.error("EXCEPTION FOUND createEC2VPC: \n"+ase.getErrorMessage());
        }
        return vpcID;
    }

    /* ****************************************************************************** */
    /**
     * This method creates a subnet for the specified VPC ID using the specified CIDR block
     * @param ec2 AmazonEC2 object
     * @param cidrBlock The IPv4 network range for the subnet, in CIDR notation
     * @param vpcID The ID of the VPC
     * */
    public String createEC2Subnet(AmazonEC2 ec2, String cidrBlock, String vpcID){
        try{
            if(vpcID==null && useDefaultVPC!=false){
                Log.info("No VPC ID found. Using default VPC");
            }
            if (cidrBlock==null){
                Log.info("No CIDR Block found. Hence creating new CIDR");
            }
            CreateSubnetRequest subnetRequest = new CreateSubnetRequest() .withVpcId(vpcID)
                    .withCidrBlock(cidrBlock);
            CreateSubnetResult result = ec2.createSubnet(subnetRequest);
            String subnetID = result.getSubnet().getSubnetId();
            Log.info("Created Subnet: "+result.getSubnet().getSubnetId());
            return subnetID;
        }
        catch (AmazonServiceException ase){
            Log.error("EXCEPTION FOUND createEC2Subnet: \n"+ase.getErrorMessage());
            return null;
        }
    }

    /* ****************************************************************************** */
    /**
     * this method creates a VPC and assigns it a subnet
     * @param ec2 AmazonEC2 instance
     * @param cidrBlock CIDR block to be used for the VPC and Subnet
     * */
    public void createEC2VPCandSubnet(AmazonEC2 ec2, String cidrBlock){
        try{
            CreateVpcRequest vpcRequest = new CreateVpcRequest().withCidrBlock(cidrBlock);
            CreateVpcResult vpcResult = ec2.createVpc(vpcRequest);
            String vpcID = vpcResult.getVpc().getVpcId();
            CreateSubnetRequest subnetRequest = new CreateSubnetRequest().withVpcId(vpcID)
                    .withCidrBlock(cidrBlock);
            CreateSubnetResult subnetResult = ec2.createSubnet(subnetRequest);
            String subnetID = subnetResult.getSubnet().getSubnetId();
        }
        catch(AmazonEC2Exception ase){
            Log.error("EXCEPTION FOUND: \n"+ase.getErrorMessage());
        }
    }

    /* ****************************************************************************** */
    /**
     * This method returns all the VPC IDs for the given client
     * @param ec2Client Amazon ec2 client
     * @return vpcIDs list of VPC-IDs
     * */
    public DescribeVpcsResult getAllVpcs(AmazonEC2 ec2Client){
        DescribeVpcsResult vpcResult = null;
        try{
            DescribeVpcsRequest vpcRequest = new DescribeVpcsRequest();
            vpcResult = ec2Client.describeVpcs(vpcRequest);
        }
        catch(Exception e){
            Log.error("EXCEPTION FOUND in getAllVpcs: \n"+e);
        }
        return vpcResult;
    }

    /* ****************************************************************************** */
    /**
     * This method returns all the VPC IDs for the given client
     * @param ec2Client Amazon ec2 client
     * @return vpcIDs list of VPC-IDs
     * */
    public List<String> getAllVpcIds(AmazonEC2 ec2Client){
        List<String> vpcIDs = new ArrayList<String>();
        try{

            DescribeVpcsResult vpcResult = getAllVpcs(ec2Client);
            for(int i=0; i<vpcResult.getVpcs().size(); i++){
                Log.info("VPCID: "+vpcResult.getVpcs().get(i).getVpcId()+"||"+"CIDR Block: "+vpcResult.getVpcs().get(i).getCidrBlock());
                String vpcId = vpcResult.getVpcs().get(i).getVpcId();
                vpcIDs.add(vpcId);
            }
        }
        catch(Exception e){
            Log.error("EXCEPTION FOUND in getAllVpcIds: \n"+e);
        }
        return vpcIDs;
    }

    /* ****************************************************************************** */
    /**
     * This method returns all the VPC IDs for the given client
     * @param ec2Client Amazon ec2 client
     * @return vpcIDs list of VPC-IDs
     * */
    public List<String> getAllVpcCidr(AmazonEC2 ec2Client){
        List<String> vpcCidr = new ArrayList<String>();
        try{

            DescribeVpcsResult vpcResult = getAllVpcs(ec2Client);
            for(int i=0; i<vpcResult.getVpcs().size(); i++){
                Log.info("VPCID: "+vpcResult.getVpcs().get(i).getVpcId()+"||"+"CIDR Block: "+vpcResult.getVpcs().get(i).getCidrBlock());
                String vpcId = vpcResult.getVpcs().get(i).getCidrBlock();
                vpcCidr.add(vpcId);
            }
        }
        catch(Exception e){
            Log.error("EXCEPTION FOUND in getAllVpcCidr: \n"+e);
        }
        return vpcCidr;
    }
    /* ****************************************************************************** */
    /**
     * This method returns the default VPC's ID
     * @param ec2Client Amazon Client
     * @return vpcId ID of the default VPC
     * */
    public String getDefaultVpcId(AmazonEC2 ec2Client){
        String vpcId = null;
        try{
            DescribeVpcsResult vpcResult = getAllVpcs(ec2Client);
            for(int i=0; i<vpcResult.getVpcs().size(); i++){
                if(vpcResult.getVpcs().get(i).getIsDefault()==true){
                    vpcId = vpcResult.getVpcs().get(i).getVpcId();
                    Log.info("The default VPC ID is: "+vpcId);
                }
            }

        }
        catch(Exception e){
            Log.error("EXCEPTION FOUND in getDefaultVpcId: \n"+e);
        }
        return vpcId;
    }

    /* ****************************************************************************** */
    /**
     * This method returns the default VPC's CIDR
     * @param ec2Client Amazon Client
     * @return vpcCidr CIDR of the default VPC
     * */
    public String getDefaultVpcCidr(AmazonEC2 ec2Client){
        String vpcCidr = null;
        try{
            DescribeVpcsResult vpcResult = getAllVpcs(ec2Client);
            for(int i=0; i<vpcResult.getVpcs().size(); i++){
                if(vpcResult.getVpcs().get(i).getIsDefault()==true){
                    vpcCidr = vpcResult.getVpcs().get(i).getCidrBlock();
                    Log.info("The default VPC CIDR is: "+vpcCidr);
                }
            }

        }
        catch(Exception e){
            Log.error("EXCEPTION FOUND in getDefaultVpcCidr: \n"+e);
        }
        return vpcCidr;
    }

    /* ****************************************************************************** */
    /**
     * This method returns all the VPC IDs for the given client
     * @param ec2Client Amazon ec2 client
     * @return vpcIDs list of VPC-IDs
     * */
    public List<String> getAllAssociatedSubnetIds(AmazonEC2 ec2Client, String vpcID){
        List<String> subnetIds = new ArrayList<String>();
        try{
            DescribeSubnetsRequest subnetRequest = new DescribeSubnetsRequest();
            DescribeSubnetsResult subnetResult = ec2Client.describeSubnets(subnetRequest);
            //Log.info(subnetResult.toString());
            for(int i=0; i<subnetResult.getSubnets().size(); i++){
                if (vpcID .equals(subnetResult.getSubnets().get(i).getVpcId())){
                    subnetIds.add(subnetResult.getSubnets().get(i).getSubnetId());
                    Log.info("Subnet :"+subnetResult.getSubnets().get(i).getSubnetId());
                }
            }
        }
        catch(Exception e){
            Log.error("EXCEPTION FOUND in getAllAssociatedSubnetIds: \n"+e);
        }
        return subnetIds;
    }

    /* ****************************************************************************** */
    /**
     * This method creates an Cloud formation client for the given region
     * @param region region where the client should be associated with
     * @return cfClient Amazon cloud formation client
     * */
    public AmazonCloudFormation createCloudFormationClient(String region){
        try{
            AWSCredentials amazonCreds = new BasicAWSCredentials(ACCESS_KEY_ID,SECRET_KEY);
            AmazonCloudFormation cfClient = AmazonCloudFormationClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(amazonCreds))
                    .withRegion(region)
                    .build();
            Log.info("Created the CloudFormation Client");
            return cfClient;
        }
        catch (AmazonServiceException ase){
            Log.error("EXCEPTION FOUND: \n"+ase.getErrorMessage());
            return null;
        }
    }

    /* ****************************************************************************** */
    /**
     * This method creates the stack with the given name for the given region
     * @param cfClient CloudFormation client used for request and response
     * @param stackName The name given to the CloudFormation stack to be created
     * @param templateBody JSON or YML template passed as a string
     * */
    public void createCloudFormationStack(AmazonCloudFormationClient cfClient, String stackName, String templateBody){
        try{
            CreateStackRequest stackRequest = new CreateStackRequest().withStackName(stackName)
                    .withTemplateBody(templateBody);
            CreateStackResult stackResult = cfClient.createStack(stackRequest);
            Log.info("Stack Created "+stackResult.getStackId());
        }
        catch (AmazonServiceException ase){
            Log.error("EXCEPTION FOUND: \n"+ase.getErrorMessage());
        }
    }

    /* ****************************************************************************** */
    /**
     * This method deletes the stack with the given name for the given region
     * @param cfClient CloudFormation client used for request and response
     * @param stackName The name given to the CloudFormation stack to be deleted
     * */
    public void deleteCloudFormationStack(AmazonCloudFormationClient cfClient, String stackName){
        try{
            DeleteStackRequest deleteRequest = new DeleteStackRequest().withStackName(stackName);
            DeleteStackResult deleteResult = cfClient.deleteStack(deleteRequest);
            Log.info("Deleted Stack: "+stackName);
        }
        catch (AmazonServiceException ase){
            Log.error("EXCEPTION FOUND: \n"+ase.getErrorMessage());
        }
    }

}

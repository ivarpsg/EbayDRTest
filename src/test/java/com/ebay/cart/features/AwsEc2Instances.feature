Feature: Create EC2 instances and delete EC2 instances

  @Ec2
  Scenario: To validate create,delete EC2 instances
    Given user creates client and instance
    Then user deletes EC2 instances

# cash-register
The solution in Java

Hello Change
At Rocketmiles, our team loves travelling. However, many places we travel don’t accept our credit cards,
and we have to remember to bring dollar bills with us. We’ve had to correct a few vendors about giving us
the correct change from their cash registers, sometimes we get too much and sometimes we get too little.
Create a cash register that should be able to accept only $20, $10, $5, $2 and $1 bills. Given the charge and
amount of money received return the change in each denomination that should be given from the cash
register. Sometimes when the vendors couldn’t make exact change they would tell us they couldn’t make
exact change.
It’s safe to assume that all output will be printed to the command line, below are some examples of how it
would be used.
// start program, waiting for command
> java <MyProgramClass...>
ready
// show current state
> show
$20 1
$10 1
$5 1
$2 3
$1 1
// add 10 $20 bill, show current state
> put 10 20
$20 2
$10 1
$5 1
$2 3
$1 1
// charge $2, given $10 assuming change can be made
> change 2 10
$5 1
$2 1
$1 1
// charge $1, given $20, assuming no change can be made
> change 1 20
sorry
// exit program
> quit
bye

 #!/bin/sh
while read l; do
	curl -X POST --header 'Content-Type: application/json' --header 'Accept: application/hal+json' -d "$l" "http://"$1":"$2"@localhost:8080/poi-api/v1/points"
done <db_init_records.dat


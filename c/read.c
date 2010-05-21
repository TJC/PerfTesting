#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/time.h>

#define MAX_LINE 1024
#define MAX_FIELDS 20

void error(char *s)
{
	fprintf(stderr, "%s\n", s);
	exit(1);
}

char *csv_read_quoted_copy(char *o, char *i)
{
	while(1) {
		char c = *i;
		if (c == '\0') {
			error("csv: mismatched quotes");
		} else if (c == '"') {
			if (i[1] == '"') {
				++i;
			} else if (i[1] == ',' || i[1] == '\0') {
				*o++ = '\0';
				++i;
				break;
			} else {
				error("csv: quote inside field must be followed by quote, comma or newline");
			}
		} else {
			*o++ = *i++;
		}
	}
	return i;
}

char *csv_read_quoted(char *p)
{
	while(1) {
		char c = *p;
		if (c == '\0') {
			error("csv: mismatched quotes");
		} else if (c == '"') {
			if (p[1] == '"') {
				char *o = p+1;
				p += 2;
				p = csv_read_quoted_copy(o, p);
				break;
			} else if (p[1] == ',' || p[1] == '\0') {
				*p++ = '\0';
				break;
			} else {
				error("csv: quote inside field must be followed by quote, comma or newline");
			}
		} else {
			++p;
		}
	}
	return p;
}

void split_csv(char *v[], char *p)
{
	*v = p;
	while(1) {
		char c = *p;
		if (c == '\0') {
			*++v = NULL;
			break;
		} else if (c == ',') {
			*p++ = '\0';
			*++v = p;
		} else if (c == '"') {
			++*v;
			p = csv_read_quoted(p+1);
		} else {
			++p;
		}
	}
}

int main(int argc, char *argv[])
{
	char *file;
	FILE *in;
	char buf[MAX_LINE];
	struct timeval start, end;
	double dur;
	char *v[MAX_FIELDS];

	gettimeofday(&start, NULL);

	if (argc != 2) {
		fprintf(stderr, "usage: %s input_file\n", argv[0]);
		exit(1);
	}
	file = argv[1];
	in = fopen(file, "r");
	if (!in)
		error("file not found");

	fgets(buf, MAX_LINE, in);

	while (fgets(buf, MAX_LINE, in)) {
		split_csv(v, buf);
		char *name = v[0];
		int i = atoi(v[1]);
		double f = atof(v[2]);
		printf("%s is %.02f\n", name, i*f);
	}

	fclose(in);
	
	gettimeofday(&end, NULL);

	dur = (end.tv_sec - start.tv_sec) + (end.tv_usec - start.tv_usec) / 1e6;

	fprintf(stderr, "Code took %f\n", dur);

	exit(0);
}


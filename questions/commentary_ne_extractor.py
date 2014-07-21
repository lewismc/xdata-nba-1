import argparse
import nltk
import pprint
import re
import requests
import sys

SOLR_URL = 'http://localhost:8983/solr/'
CORE = 'game-commentary/'
QUERY_STRING = '?q=game_id%3A{game_id}&wt=json&indent=true'

def detect_named_entities_in_commentary(game_id):
    query = SOLR_URL + CORE + 'select/' + QUERY_STRING
    query = query.format(game_id=game_id)

    results = run_query(query)
    doc = strip_doc_list_from_results(results)

    prepared_preview = prep_for_named_entity_extraction(doc['preview'])
    prepared_recap = prep_for_named_entity_extraction(doc['recap'])
    prepared_notebook = prep_for_named_entity_extraction(doc['notebook'])

    preview_ne = set(extract_entity_names(prepared_preview))
    recap_ne = set(extract_entity_names(prepared_recap))
    notebook_ne = set(extract_entity_names(prepared_notebook))

    named_entities = preview_ne.union(recap_ne.union(notebook_ne))
    return named_entities

def prep_for_named_entity_extraction(string):
    return nltk.ne_chunk(nltk.pos_tag(nltk.word_tokenize(string)), binary=True)

def extract_entity_names(tree):
    entity_names = []
    
    if hasattr(tree, 'node') and tree.node:
        if tree.node == 'NE':
            entity_names.append(' '.join([child[0] for child in tree]))
        else:
            for child in tree:
                entity_names.extend(extract_entity_names(child))
                
    return entity_names

def run_query(query):
    ''''''
    r = requests.get(query)
    return r.json()

def strip_doc_list_from_results(query_results):
    ''''''
    if query_results['response']['numFound'] == 0:
        print "No documents were returned"
        sys.exit(1)

    return query_results['response']['docs'][0]

if __name__ == '__main__':
    parser = argparse.ArgumentParser(description='XDATA NBA NE Extractor')
    parser.add_argument('game_id', help='NBA data game id')
    args = vars(parser.parse_args())

    print detect_named_entities_in_commentary(**args)
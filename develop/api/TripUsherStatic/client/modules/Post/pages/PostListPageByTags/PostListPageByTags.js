import React, { PropTypes, Component } from 'react';
import { connect } from 'react-redux';
import Helmet from 'react-helmet';

 // Import Style
import styles from '../../components/PostListItem/PostListItem.css';

// Import Actions
import { fetchPostsByTagsAction } from '../../PostActions';

// Import Selectors
import { getPostsByTags } from '../../PostReducer';
 
    
  export function PostListPageByTags(props) {
  return (
    <div>
      <Helmet title={props.post.title} />
      <div className={`${styles['single-post']} ${styles['post-detail']}`}>
        <h3 className={styles['post-title']}>{props.post.title}</h3>
        <p className={styles['author-name']}><FormattedMessage id="by" /> {props.post.name}</p>
        <div dangerouslySetInnerHTML={{__html: props.post.content}} />   
      </div>
    </div>
  );
}

// Actions required to provide data for this component to render in sever side.
PostListPageByTags.need = [params => {
  return fetchPostsByTagsAction(params.tags);
}];

// Retrieve data from store as props
function mapStateToProps(state, props) {
  return {
    post: getPostsByTags(state, props.params.tags),
  };
}

PostListPageByTags.propTypes = {
  posts: PropTypes.arrayOf(PropTypes.shape({
    name: PropTypes.string.isRequired,
    title: PropTypes.string.isRequired,
    tags: PropTypes.string.isRequired,
    content: PropTypes.string.isRequired,
  })).isRequired,  
};

PostListPageByTags.contextTypes = {
  router: React.PropTypes.object,
};

export default connect(mapStateToProps)(PostListPageByTags);

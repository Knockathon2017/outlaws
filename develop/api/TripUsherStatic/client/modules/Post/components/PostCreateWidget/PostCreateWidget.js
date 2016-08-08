import React, { Component, PropTypes } from 'react';
import TinyMCE from 'react-tinymce';
import { injectIntl, intlShape, FormattedMessage } from 'react-intl';


// Import Style
import styles from './PostCreateWidget.css';

export class PostCreateWidget extends Component {
  addPost = () => {
    const nameRef = this.refs.name;
    const titleRef = this.refs.title;
    const contentRef = this.refs.content;
    const tagsRef = this.refs.tags;
    if (nameRef.value && titleRef.value && window.parent.tinymce.activeEditor.getContent() && tagsRef.value) {
      this.props.addPost(nameRef.value, titleRef.value, tagsRef.value, window.parent.tinymce.activeEditor.getContent());
      nameRef.value = titleRef.value = contentRef.value =  tagsRef.value = '';
      window.parent.tinymce.activeEditor.setContent('');
    }
  };

  render() {
    const cls = `${styles.form} ${(this.props.showAddPost ? styles.appear : '')}`;
    return (
      <div className={cls}>
        <div className={styles['form-content']}>
          <h2 className={styles['form-title']}><FormattedMessage id="createNewPost" /></h2>
          <input placeholder={this.props.intl.messages.authorName} className={styles['form-field']} ref="name" />
          <input placeholder={this.props.intl.messages.postTitle} className={styles['form-field']} ref="title" />
          <input placeholder={this.props.intl.messages.postTags} className={styles['form-field']} ref="tags" />          
          <TinyMCE  placeholder={this.props.intl.messages.postContent} className={styles['form-field']} 
          config={{plugins: 'autolink link image lists print preview',
          toolbar: 'undo redo | bold italic | alignleft aligncenter alignright'}}  ref="content"/>       
         <br/>
          <a className={styles['post-submit-button']} href="#" onClick={this.addPost}><FormattedMessage id="submit" /></a>
        </div>
      </div>
    );
  }
}

PostCreateWidget.propTypes = {
  addPost: PropTypes.func.isRequired,
  showAddPost: PropTypes.bool.isRequired,
  intl: intlShape.isRequired,
};

export default injectIntl(PostCreateWidget);
